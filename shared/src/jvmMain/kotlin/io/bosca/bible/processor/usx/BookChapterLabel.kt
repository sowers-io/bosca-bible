package io.bosca.bible.processor.usx

import io.bosca.bible.BookChapterLabelStyle
import io.bosca.bible.processor.Context

class BookChapterLabel(
    context: Context,
    parent: Usx?,
    attributes: Attributes
) : ItemContainer<Text>(context, parent),
    RootItem {

    val style = BookChapterLabelStyle.valueOf(attributes["STYLE"] ?: error("missing style"))

    override val htmlClass = style.toString()
}

object BookChapterLabelFactory : ItemFactory<BookChapterLabel>("para") {

    override fun onInitialize() {
        register(TextFactory)
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) =
        BookChapterLabel(context, parent, attributes ?: error("missing attributes"))
}
