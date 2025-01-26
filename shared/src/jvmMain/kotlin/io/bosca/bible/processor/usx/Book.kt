package io.bosca.bible.processor.usx

import ManifestName
import PublicationContent
import io.bosca.bible.processor.Position

class Book(val name: ManifestName, val content: PublicationContent, val raw: String) {

    private val _chapters = mutableListOf<Chapter>()
    private val _chaptersByUsfm = mutableMapOf<String, Chapter>()

    val usfm: String
        get() = content.usfm

    val chapters: kotlin.collections.List<Chapter>
        get() = _chapters

    fun addChapter(chapter: Chapter) {
        _chapters.add(chapter)
        _chaptersByUsfm[chapter.usfm] = chapter
    }

    fun getRawContent(position: Position): String {
        return this.raw.substring(position.start, position.end)
    }
}