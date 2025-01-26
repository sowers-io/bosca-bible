package io.bosca.bible.processor.usx

import io.bosca.bible.processor.CompletedBookTag
import io.bosca.bible.processor.Context

// export type ChapterType = Paragraph | List | Table | Footnote | CrossReference | Sidebar | ChapterEnd
interface ChapterItem: Item

class Chapter(
    context: Context,
    parent: Item?,
    book: Book,
    val start: ChapterStart,
): ItemContainer<ChapterItem>(context, parent) {

    private val verseItems = mutableMapOf<String, MutableList<VerseItems>>()
    private val _end: ChapterEnd? = null

    val number = start.number
    val usfm = "${book.usfm}.${start.number}"

    override val htmlClass = "chapter c$number"
    override val htmlAttributes = mapOf(
        "data-usfm" to usfm,
        "data-number" to number,
        *super.htmlAttributes.toList().toTypedArray()
    )

    var end: ChapterEnd
        get() = _end ?: error("Chapter end not defined.")
        set(end) {
            if (_end != null) error("Chapter end already defined.")
            _end
        }

    fun addVerseItems(items: kotlin.collections.List<VerseItems>) {
        items.forEach {
            var current = verseItems[it.usfm]
            if (current == null) {
                current = mutableListOf()
                verseItems[it.usfm] = current
            }
            current.add(it)
        }
    }

    override fun add(item: ChapterItem) {
        if (item is ChapterEnd) return
        super.add(item)
    }
}

/*
export class Chapter extends UsxItemContainer<ChapterType> {

  getVerses(book: Book): ChapterVerse[] {
    const verses: ChapterVerse[] = []
    for (const usfm in this.verseItems) {
      const items = this.verseItems[usfm]
      const usfmParts = usfm.split('.')
      let raw = ''
      for (const item of items) {
        raw += book.getRawContent(item.position)
      }
      verses.push(new ChapterVerse(
        usfm,
        this.number,
        usfmParts[usfmParts.length - 1],
        items,
        raw,
      ))
    }

    function getNumber(value: string): number {
      let number = parseInt(value)
      if (isNaN(number)) number = 0
      return number
    }

    verses.sort((a, b) => {
      const aChapter = getNumber(a.chapter)
      const bChapter = getNumber(b.chapter)
      if (aChapter > bChapter) return 1
      if (aChapter < bChapter) return -1

      const aVerse = getNumber(a.verse)
      const bVerse = getNumber(b.verse)
      if (aVerse > bVerse) return 1
      if (aVerse < bVerse) return -1
      return 0
    })

    return verses
  }
}
 */