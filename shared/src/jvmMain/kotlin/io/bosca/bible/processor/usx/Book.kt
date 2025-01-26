package io.bosca.bible.processor.usx

import io.bosca.bible.processor.Position

class Book(
    private val name: ManifestName,
    private val content: PublicationContent,
    private val raw: String
) {

    private val _chapters = mutableListOf<Chapter>()
    private val _chaptersByUsfm = mutableMapOf<String, Chapter>()

    val usfm: String
        get() = content.usfm

    val chapters: kotlin.collections.List<Chapter>
        get() = _chapters

    val chaptersByUsfm: Map<String, Chapter>
        get() = _chaptersByUsfm

    fun addChapter(chapter: Chapter) {
        _chapters.add(chapter)
        _chaptersByUsfm[chapter.usfm] = chapter
    }

    fun getRawContent(position: Position): String {
        return this.raw.substring(position.start, position.end)
    }
}