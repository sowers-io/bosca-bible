package io.bosca.bible.processor.usx

import io.bosca.bible.FootnoteStyle
import io.bosca.bible.processor.Context
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.StringContext

interface FootnoteItem : Item

class Footnote(context: Context, parent: Item?, attributes: Attributes) : ItemContainer<FootnoteItem>(context, parent),
    RootItem,
    CharItem,
    ParagraphItem,
    BookIntroductionItem,
    BookIntroductionEndTitleItem,
    ListCharItem {

    val style = FootnoteStyle.valueOf(attributes["STYLE"] ?: error("missing style"))
    val caller = attributes["CALLER"] ?: error("missing caller")
    val category = attributes["CATEGORY"]

    override val htmlClass = style.toString()

    override fun toHtml(context: HtmlContext): String {
        if (!context.includeFootNotes) return ""
        return super.toHtml(context)
    }

    override fun toString(context: StringContext): String {
        if (!context.includeFootNotes) return ""
        return super.toString(context)
    }
}

object FootnoteFactory :
    ItemFactory<Footnote>("note", StyleFactoryFilter(FootnoteStyle.entries) { FootnoteStyle.valueOf(it) }) {

    override fun onInitialize() {
        register(FootnoteCharFactory)
        register(TextFactory)
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) =
        Footnote(context, parent, attributes ?: error("missing attributes"))
}
