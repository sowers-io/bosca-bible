package io.bosca.bible

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    override val reference: Reference,
    override val name: Name,
    override val chapters: List<Chapter>
): IBook {

    private val chaptersByUsfm = chapters.associateBy { it.reference.chapterUsfm }

    override fun get(reference: Reference): IChapter? = chaptersByUsfm[reference.chapterUsfm]
}