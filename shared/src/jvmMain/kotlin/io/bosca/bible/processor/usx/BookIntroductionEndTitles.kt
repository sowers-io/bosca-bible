package io.bosca.bible.processor.usx

import io.bosca.bible.BookIntroductionEndTitleStyle
import io.bosca.bible.processor.Context

interface BookIntroductionEndTitleItem : Item

class BookIntroductionEndTitles(
    context: Context,
    parent: Item?,
    attributes: Attributes
) : ItemContainer<BookIntroductionEndTitleItem>(context, parent),
    RootItem {

    val style = BookIntroductionEndTitleStyle.valueOf(attributes["STYLE"] ?: error("missing style"))

    override val htmlClass = style.toString()
}

object BookIntroductionEndTitleFactory : ItemFactory<BookIntroductionEndTitles>("para") {
    override fun onInitialize() {
        register(FootnoteFactory)
        register(CrossReferenceFactory)
        register(CharFactory)
        register(MilestoneFactory)
        register(BreakFactory)
        register(TextFactory)
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) =
        BookIntroductionEndTitles(context, parent, attributes ?: error("missing attributes"))
}
