package io.bosca.bible.processor.usx

import io.bosca.bible.BookHeaderStyle
import io.bosca.bible.processor.Context

class BookHeader(
    context: Context,
    parent: Item?,
    attributes: Attributes
) : ItemContainer<Text>(context, parent), UsxItem {

    val style: BookHeaderStyle = BookHeaderStyle.valueOf(attributes["STYLE"] ?: error("missing style"))

    override val htmlClass get() = style.name
}

object BookHeaderFactory : ItemFactory<BookHeader>("para") {

    override fun onInitialize() {
        register(TextFactory)
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) =
        BookHeader(context, parent, attributes!!)
}
