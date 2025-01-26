package io.bosca.bible.processor.usx

import io.bosca.bible.FootnoteCharStyle
import io.bosca.bible.processor.Context
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.StringContext

// type FootnoteCharType = Char | FootnoteChar | FootnoteVerse | Reference | Text
interface FootnoteCharItem : Item

class FootnoteChar(context: Context, parent: Item?, attributes: Attributes) :
    ItemContainer<FootnoteCharItem>(context, parent),
    ListCharItem {

    val style = FootnoteCharStyle.valueOf(attributes["STYLE"] ?: error("missing style"))
    // char.link?,
    // char.closed?,

    override val htmlClass = style.toString()

    override fun toHtml(context: HtmlContext): String {
        if (!context.includeFootNotes) return ""
        return super.toHtml(context)
    }

    override fun toString(context: StringContext): String {
        if (!context.includeFootNotes) return ""
        return super.toString()
    }
}

object FootnoteCharFactory :
    ItemFactory<FootnoteChar>("char", StyleFactoryFilter(FootnoteCharStyle.entries) { FootnoteCharStyle.valueOf(it) }) {

    override fun onInitialize() {
        register(CharFactory)
        register(TextFactory)
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) =
        FootnoteChar(context, parent, attributes ?: error("missing attributes"))
}
