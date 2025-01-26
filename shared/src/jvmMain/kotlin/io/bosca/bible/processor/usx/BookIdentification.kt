package io.bosca.bible.processor.usx

import io.bosca.bible.BookIdentificationCode
import io.bosca.bible.processor.Context

class BookIdentification(
    context: Context,
    parent: Item?,
    attributes: Attributes?
) : ItemContainer<Text>(context, parent), UsxItem {

    val id: String = attributes?.get("STYLE") ?: ""
    val code: BookIdentificationCode = BookIdentificationCode.valueOf(attributes?.get("CODE") ?: error("No code provided for book identification."))

    override val htmlClass = "book-identification"
    override val htmlAttributes = mapOf(
        "data-id" to id,
        "data-code" to code.name,
    ) + super.htmlAttributes
}


object BookIdentificationFactory: ItemFactory<BookIdentification>(
    "book",
    CodeFactoryFilter(BookIdentificationCode.entries.toSet())
) {

    override fun onInitialize() {
        register(TextFactory)
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) = BookIdentification(context, parent, attributes!!)
}