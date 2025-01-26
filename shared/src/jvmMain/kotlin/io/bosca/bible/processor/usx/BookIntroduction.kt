package io.bosca.bible.processor.usx

import io.bosca.bible.BookIntroductionStyle
import io.bosca.bible.processor.Context

interface BookIntroductionItem : Item

class BookIntroduction(
    context: Context,
    parent: Item?,
    attributes: Attributes
) : ItemContainer<BookIntroductionItem>(context, parent),
    RootItem {

    val style = BookIntroductionStyle.valueOf(attributes["STYLE"] ?: error("missing style"))

    override val htmlClass = "book-introduction $style"
}

object BookIntroductionFactory : ItemFactory<BookIntroduction>("para") {

    override fun onInitialize() {
        register(ReferenceFactory)
        register(FootnoteFactory)
        register(CrossReferenceFactory)
        register(CharFactory)
        register(IntroCharFactory)
        register(MilestoneFactory)
        register(FigureFactory)
        register(TextFactory)
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) =
        BookIntroduction(context, parent, attributes!!)
}

object BookIntroductionTableFactory : ItemFactory<BookIntroduction>("table") {

    override fun onInitialize() {
        register(TableFactory)
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) =
        BookIntroduction(context, parent, attributes!!)
}
