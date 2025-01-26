package io.bosca.bible.processor.usx

import io.bosca.bible.ListStyle
import io.bosca.bible.processor.Context

// type ListType = Reference | Footnote | CrossReference | Char | ListChar | Milestone | Figure | Break

interface ListItem : Item

class List(
    context: Context,
    parent: Item?,
    attributes: Attributes
) : ItemContainer<ListItem>(context, parent),
    RootItem {

    val style = ListStyle.valueOf(attributes["STYLE"] ?: error("missing style"))
    val vid = attributes["VID"]

    override val htmlClass = style.toString()
}

object ListFactory : ItemFactory<List>("para", StyleFactoryFilter(ListStyle.entries) { ListStyle.valueOf(it) }) {

    override fun onInitialize() {
        register(ReferenceFactory)
        register(FootnoteFactory)
        register(CrossReferenceFactory)
        register(CharFactory)
        register(ListCharFactory)
        register(MilestoneFactory)
        register(FigureFactory)
        register(VerseStartFactory)
        register(VerseEndFactory)
        register(BreakFactory)
        register(TextFactory)
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) =
        List(context, parent, attributes ?: error("missing attributes"))
}