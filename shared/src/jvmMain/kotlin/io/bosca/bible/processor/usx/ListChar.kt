package io.bosca.bible.processor.usx

import io.bosca.bible.ListCharStyle
import io.bosca.bible.processor.Context

// type ListCharType = Reference | Char | Milestone | Footnote | Break | Text
interface ListCharItem : Item

class ListChar(
    context: Context,
    parent: Item?,
    attributes: Attributes,
) : ItemContainer<ListCharItem>(context, parent),
    BookIntroductionItem,
    ListItem {

    val style = ListCharStyle.valueOf(attributes["STYLE"] ?: error("missing style"))
    // char.link?
    // char.closed?

    override val htmlClass = style.toString()
}

object ListCharFactory :
    ItemFactory<ListChar>("char", StyleFactoryFilter(ListCharStyle.entries) { ListCharStyle.valueOf(it) }) {

    override fun onInitialize() {
        register(ReferenceFactory)
        register(CharFactory)
        register(MilestoneFactory)
        register(FootnoteFactory)
        register(BreakFactory)
        register(TextFactory)
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) =
        ListChar(context, parent, attributes ?: error("missing attributes"))
}