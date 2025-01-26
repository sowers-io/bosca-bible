package io.bosca.bible.processor.usx

import io.bosca.bible.CharStyle
import io.bosca.bible.processor.Context

interface CharItem : Item

class Char(
    context: Context,
    parent: Item?,
    attributes: Attributes
) : ItemContainer<CharItem>(context, parent),
    CharItem,
    ParagraphItem,
    BookIntroductionItem,
    BookIntroductionEndTitleItem,
    FootnoteCharItem,
    ListCharItem {

    val style = CharStyle.valueOf(attributes["STYLE"] ?: error("missing style"))

    override val htmlClass = style.toString()
}

object CharFactory : ItemFactory<Char>("char", StyleFactoryFilter(CharStyle.entries) { CharStyle.valueOf(it) }) {
    override fun onInitialize() {
        register(ReferenceFactory)
        register(CharFactory)
        register(MilestoneFactory)
        register(FootnoteFactory)
        register(BreakFactory)
        register(TextFactory)
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) =
        Char(context, parent, attributes ?: error("missing attributes"))
}
