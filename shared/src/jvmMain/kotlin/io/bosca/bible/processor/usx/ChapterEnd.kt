package io.bosca.bible.processor.usx

import io.bosca.bible.processor.Context
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.StringContext

class ChapterEnd(
    context: Context,
    parent: Item?,
    attributes: Attributes
) : AbstractItem(context, parent),
    RootItem,
    ChapterItem {

    val eid: String = attributes["EID"] ?: error("missing eid")

    override val htmlClass: String = ""
    override val htmlAttributes: Map<String, String> = emptyMap()

    override fun toHtml(context: HtmlContext): String = ""
    override fun toString(context: StringContext): String = ""
}

object ChapterEndFactory : ItemFactory<ChapterEnd>("chapter", EndIdFactoryFilter()) {
    override fun onInitialize() {
    }

    override fun create(
        context: Context,
        parent: Item?,
        attributes: Attributes?
    ): ChapterEnd = ChapterEnd(context, parent, attributes ?: error("missing attributes"))
}
