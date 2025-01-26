package io.bosca.bible.processor

import io.bosca.bible.*
import io.bosca.bible.processor.usx.*
import io.bosca.bible.processor.usx.Book
import io.bosca.bible.processor.usx.Chapter
import java.util.*

internal enum class CompletedBookTag {
    identification,
    headers,
    titles,
    introduction,
    endIntroductionTitles,
    label,
    chapter,
}

internal enum class BookTagResult {
    supported,
    unsupported,
    unknown,
}

class Context(private val book: Book) {

    private val chapters = mutableListOf<Chapter>()
    private val nodes = mutableListOf<Node>()
    private val completed = mutableListOf<CompletedBookTag>()
    private val verseItems = mutableListOf<VerseItems>()
    private var lastFactory: ItemFactory<*>? = null

    private var progression = true

    private val positions = Stack<Position>()
    private val verses = Stack<VerseItems>()

    val position: Position
        get() = positions.last()

    fun pushVerse(bookChapterUsfm: String, verse: VerseStart, position: Position) {
        verses.push(VerseItems("$bookChapterUsfm.${verse.number}", position, verse))
    }

    fun popVerse(): VerseItems = verses.pop()

    val verse: String?
        get() {
            if (verses.isEmpty()) return null
            return verses.peek()?.verse
        }

    fun add(parent: Usx?, item: Item): VerseStart? {
        if (verses.isEmpty()) return null
        val verses = this.verses.last()
        if (parent == null || parent.verse != verses.verse) {
            verses.add(item)
        }
        return verses.verseStart
    }

    fun supports(factory: ItemFactory<*>, parent: Node, tag: Tag, progression: Int? = null): Boolean {
        return factory.supports(this, tag.attributes, progression)
    }

    private class ContextTag(
        val factory: ItemFactory<*>,
        val tag: CompletedBookTag,
        val maxStyles: Int,
    )

    private val tags = listOf(
        ContextTag(BookIdentificationFactory, CompletedBookTag.identification, 1),
        ContextTag(BookHeaderFactory, CompletedBookTag.headers, BookHeaderStyle.entries.size),
        ContextTag(BookTitleFactory, CompletedBookTag.titles, BookTitleStyle.entries.size),
        ContextTag(BookIntroductionFactory, CompletedBookTag.introduction, BookIntroductionStyle.entries.size),
        ContextTag(BookIntroductionEndTitleFactory, CompletedBookTag.endIntroductionTitles, BookIntroductionEndTitleStyle.entries.size),
        ContextTag(BookChapterLabelFactory, CompletedBookTag.label, BookChapterLabelStyle.entries.size),
    )

    private fun supportsInternal(factory: ItemFactory<*>, parent: Node, tag: Tag): BookTagResult {
        var tagIndex = -1
        do {
            tagIndex++
            if (tagIndex >= tags.size) return BookTagResult.unknown
            val tagFactory = tags[tagIndex]
            if (completed.contains(tagFactory.tag)) {
                if (tagFactory.factory === factory) {
                    return BookTagResult.unsupported
                }
                continue
            }
            if (tagFactory.factory === factory) {
                if (tagIndex > 0) {
                    val previousTag = tags[tagIndex - 1]
                    if (!completed.contains(previousTag.tag)) {
                        return BookTagResult.unsupported
                    }
                }
                for (i in 0 until tagFactory.maxStyles) {
                    if (supports(tagFactory.factory, parent, tag, i)) {
                        if (i + 1 == tagFactory.maxStyles) {
                            completed.add(tagFactory.tag)
                        }
                        return BookTagResult.supported
                    }
                }
                if (factory === lastFactory) {
                    completed.add(tagFactory.tag)
                }
                return BookTagResult.unsupported
            }
        } while (tagIndex < tags.size)
        return BookTagResult.unknown
    }

    fun supports(factory: ItemFactory<*>, parent: Node, tag: Tag): Boolean {
        if (tag.name.lowercase() == "chapter") {
            this.progression = false
        }
        if (!this.progression || tag.name == "#text" || parent.factory !== RootFactory) {
            if (!this.progression) {
                for (t in this.tags) {
                    if (t.factory === factory) return false
                }
            }
            return factory.supports(this, tag.attributes, null)
        }
        when (this.supportsInternal(factory, parent, tag)) {
            BookTagResult.supported -> {
                this.lastFactory = factory
                return true
            }
            BookTagResult.unsupported -> return false
            BookTagResult.unknown -> return !this.progression && this.supports(factory, parent, tag)
        }
    }

    fun addText(text: String, position: Int) {
        positions.add(Position(position))
        if (nodes.isEmpty()) return
        val node = push(Tag("#text"), position)
        if (node is Text) {
            node.text = text
        } else {
            error("unsupported text node")
        }
        pop(position + text.length)
    }

    fun push(tag: Tag, position: Int): Item {
        positions.add(Position(position))
        if (tag.name.lowercase() == "usx") {
            val factory = RootFactory
            val item = factory.create(this, null, tag.attributes)
            nodes.add(Node(factory, item, position))
            return item
        }
        if (nodes.isEmpty()) {
            error("empty stack, invalid state")
        }
        val node = nodes.last()
        val factory = node.factory.findChildFactory(this, node, tag)
        val item = factory.create(this, node.item, tag.attributes)
        if (item is ChapterStart) {
            progression = false
            positions.push(Position(position))
            val chapter = Chapter(this, null, book, item)
            positions.pop()
            chapters.add(chapter)
            nodes.add(Node(node.factory, chapter, position))
        } else if (item is VerseStart) {
            pushVerse(chapters.last().usfm, item, Position(item.position.start))
        }
        if (node.item is ItemContainer<*>) {
            @Suppress("UNCHECKED_CAST")
            (node.item as ItemContainer<Item>).add(item)
        }
        nodes.add(Node(factory, item, position))
        return item
    }

    fun pop(position: Int) {
        val lastPosition = positions.pop()
        lastPosition.end = position
        val node = nodes.removeLastOrNull()
        if (node != null) {
            if (node.item is VerseEnd) {
                val verse = popVerse()
                verse.position.end = position
                verseItems.add(verse)
            } else if (node.item is ChapterEnd) {
                val chapterNode = nodes.removeLastOrNull() ?: error("missing chapter node")
                val chapter = chapterNode.item as Chapter
                chapter.end = node.item
                chapter.position.start = chapter.start.position.start
                chapter.position.end = node.item.position.end
                chapter.addVerseItems(verseItems.toList())
                verseItems.clear()
                book.addChapter(chapter)
            }
        }
    }
}