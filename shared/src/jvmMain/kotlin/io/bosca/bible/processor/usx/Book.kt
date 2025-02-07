package io.bosca.bible.processor.usx

import io.bosca.bible.IBook
import io.bosca.bible.IChapter
import io.bosca.bible.IName
import io.bosca.bible.Reference
import io.bosca.bible.processor.Position

class Book(
    override val name: ManifestName,
    private val content: PublicationContent,
    private val raw: String
) : IBook {

    private val _chapters = mutableListOf<IChapter>()
    private val _chaptersByUsfm = mutableMapOf<String, IChapter>()

    override val reference: Reference by lazy {
        Reference(content.usfm)
    }

    override val chapters: kotlin.collections.List<IChapter>
        get() = _chapters

    override fun get(reference: Reference): IChapter? {
        return _chaptersByUsfm[reference.chapterUsfm]
    }

    fun addChapter(chapter: IChapter) {
        _chapters.add(chapter)
        _chaptersByUsfm[chapter.reference.chapterUsfm] = chapter
    }

    fun getRawContent(): String {
        return this.raw
    }

    fun getRawContent(position: Position): String {
        return this.raw.substring(position.start, position.end)
    }
}