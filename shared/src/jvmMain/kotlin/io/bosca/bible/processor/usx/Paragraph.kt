package io.bosca.bible.processor.usx

import io.bosca.bible.ParaStyle
import io.bosca.bible.processor.Context
import io.bosca.bible.processor.HtmlContext

interface ParagraphItem : Item

class Paragraph(
    context: Context,
    parent: Item?,
    attributes: Attributes
) : ItemContainer<Item>(context, parent),
    RootItem,
    ChapterItem {

    val style: ParaStyle = ParaStyle.valueOf(attributes["STYLE"] ?: error("missing style"))
    val vid: String? = attributes["VID"]

    override val htmlClass = style.toString()

    override fun toHtml(context: HtmlContext) = context.render("p", this)
}

object ParagraphFactory : ItemFactory<Paragraph>("para") {
    override fun onInitialize() {
        register(ReferenceFactory)
        register(FootnoteFactory)
        register(CrossReferenceFactory)
        register(CharFactory)
        register(MilestoneFactory)
        register(FigureFactory)
        register(VerseStartFactory)
        register(VerseEndFactory)
        register(BreakFactory)
        register(TextFactory)
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) =
        Paragraph(context, parent, attributes ?: error("missing attributes"))
}
