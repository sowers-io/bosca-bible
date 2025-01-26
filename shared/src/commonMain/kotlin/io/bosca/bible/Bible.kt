package io.bosca.bible

import kotlinx.serialization.Serializable

@Serializable
class Bible(override val books: List<Book>) : IBible {

    private val booksByUsfm = books.associateBy { it.reference.bookUsfm }

    override fun get(reference: Reference) = booksByUsfm[reference.bookUsfm]
}