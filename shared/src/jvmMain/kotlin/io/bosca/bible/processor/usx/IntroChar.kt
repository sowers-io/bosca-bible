package io.bosca.bible.processor.usx

import io.bosca.bible.IntroCharStyle
import io.bosca.bible.processor.Context

// type IntroCharType = Reference | Char | IntroChar | Milestone | Footnote | Break | Text
interface IntroCharItem : Item

class IntroChar(
    context: Context,
    parent: Item?,
    attributes: Attributes,
) : ItemContainer<IntroCharItem>(context, parent),
    BookIntroductionItem {
    val style = IntroCharStyle.valueOf(attributes["STYLE"] ?: error("missing style"))
    // char.closed?

    override val htmlClass: String = style.toString()
}

object IntroCharFactory :
    ItemFactory<IntroChar>("char", StyleFactoryFilter(IntroCharStyle.entries) { IntroCharStyle.valueOf(it) }) {

    override fun onInitialize() {
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) =
        IntroChar(context, parent, attributes ?: error("missing attributes"))
}
