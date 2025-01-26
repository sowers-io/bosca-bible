package io.bosca.bible.processor.usx

import io.bosca.bible.processor.Context
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.StringContext

class Break(context: Context, parent: Item?) : AbstractItem(context, parent),
    ParagraphItem,
    CharItem,
    TableContentItem,
    BookIntroductionEndTitleItem,
    ListCharItem {

    override val htmlClass = ""
    override val htmlAttributes = emptyMap<String, String>()

    override fun toHtml(context: HtmlContext) = context.render("br", this)
    override fun toString(context: StringContext) = if (context.includeNewLines) "\r\n" else ""
}

object BreakFactory : ItemFactory<Break>("optbreak") {

    override fun onInitialize() {
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) = Break(context, parent)
}
