package io.bosca.bible.processor.usx

import io.bosca.bible.IBible
import io.bosca.bible.IBook
import io.bosca.bible.Reference
import kotlin.collections.List

class Bible(override val books: List<IBook>) : IBible {

    private val booksByReference: Map<String, IBook> = books.associateBy { it.reference.usfm }

    override operator fun get(reference: Reference) = booksByReference[reference.bookUsfm]
}