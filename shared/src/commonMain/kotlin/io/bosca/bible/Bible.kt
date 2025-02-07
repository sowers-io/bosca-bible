package io.bosca.bible

import io.bosca.bible.components.IStyle
import kotlinx.serialization.Serializable

@Serializable
class Bible(override val books: List<Book>, override val styles: List<IStyle>) : IBible {

    private val booksByUsfm = books.associateBy { it.reference.bookUsfm }

    override fun get(reference: Reference) = booksByUsfm[reference.bookUsfm]
}
