package io.bosca.bible.processor.usx

import io.bosca.bible.IChapter
import io.bosca.bible.Reference
import io.bosca.bible.processor.Context
import io.bosca.bible.processor.StringContext
import kotlin.collections.List

// export type ChapterType = Paragraph | List | Table | Footnote | CrossReference | Sidebar | ChapterEnd
interface ChapterItem : Item

class ChapterVerse(
    val usfm: String,
    val chapter: String,
    val verse: String,
    val items: List<VerseItems>,
    val raw: String,
) {

    fun toString(context: StringContext = StringContext.default): String {
        var buf = ""
        for (item in this.items) {
            buf += item.toString(context)
        }
        return buf.trim()
    }

    override fun toString() = toString(StringContext.default)
}

class Chapter(
    context: Context,
    parent: Item?,
    book: Book,
    val start: ChapterStart,
) : ItemContainer<ChapterItem>(context, parent), IChapter {

    private val verseItems = mutableMapOf<String, MutableList<VerseItems>>()
    private val _end: ChapterEnd? = null

    val number = start.number

    override val reference = Reference("${book.reference.usfm}.${start.number}")

    override val htmlClass = "chapter c$number"
    override val htmlAttributes = mapOf(
        "data-usfm" to reference.usfm,
        "data-number" to number,
        *super.htmlAttributes.toList().toTypedArray()
    )

    var end: ChapterEnd
        get() = _end ?: error("Chapter end not defined.")
        set(end) {
            if (_end != null) error("Chapter end already defined.")
            _end
        }

    fun addVerseItems(items: List<VerseItems>) {
        items.forEach {
            var current = verseItems[it.reference.usfm]
            if (current == null) {
                current = mutableListOf()
                verseItems[it.reference.usfm] = current
            }
            current.add(it)
        }
    }

    override fun add(item: ChapterItem) {
        if (item is ChapterEnd) return
        super.add(item)
    }

    fun getVerses(book: Book): List<ChapterVerse> {
        val verses = mutableListOf<ChapterVerse>()
        for ((usfm, items) in this.verseItems.entries) {
            val usfmParts = usfm.split('.')
            var raw = ""
            for (item in items) {
                raw += book.getRawContent(item.position)
            }
            verses.add(
                ChapterVerse(
                    usfm,
                    this.number,
                    usfmParts.last(),
                    items,
                    raw,
                )
            )
        }
        verses.sortWith { a, b ->
            val aChapter = a.chapter.toIntOrNull() ?: 0
            val bChapter = b.chapter.toIntOrNull() ?: 0
            if (aChapter > bChapter) return@sortWith 1
            if (aChapter < bChapter) return@sortWith -1
            val aVerse = a.verse.toIntOrNull() ?: 0
            val bVerse = b.verse.toIntOrNull() ?: 0
            if (aVerse > bVerse) return@sortWith 1
            if (aVerse < bVerse) return@sortWith -1
            0
        }
        return verses
    }
}