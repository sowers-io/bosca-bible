package io.bosca.bible.processor.usx

import io.bosca.bible.BookTitleStyle
import io.bosca.bible.processor.Context

interface BookTitleItem : Item

//type BookTitleType = Footnote | CrossReference | Char | Break

class BookTitle(
    context: Context,
    parent: Item?,
    attributes: Attributes
) : ItemContainer<BookTitleItem>(context, parent),
    RootItem {

    val style: BookTitleStyle = BookTitleStyle.valueOf(attributes["STYLE"] ?: error("missing style"))

    override val htmlClass = style.toString()
}

object BookTitleFactory : ItemFactory<BookTitle>("para") {
    override fun onInitialize() {
        register(TextFactory)
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) =
        BookTitle(context, parent, attributes ?: error("missing attributes"))
}
