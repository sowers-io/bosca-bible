package io.bosca.bible.processor.usx

import kotlin.collections.List

class Bible(val books: List<Book>) : io.bosca.bible.Bible {

    val booksByUsfm = books.associateBy { it.usfm }
}