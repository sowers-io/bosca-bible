package io.bosca.bible.processor.usx

import io.bosca.bible.processor.Context

/*
type UsxType =
  BookIdentification
  | BookHeader
  | BookTitle
  | BookIntroduction
  | BookIntroductionEndTitle
  | BookChapterLabel
  | ChapterStart
  | ChapterEnd
*/

interface RootItem : Item

class Root(
    context: Context,
    parent: Usx?
) : ItemContainer<RootItem>(context, parent) {

    override val htmlClass: String = ""
}

object RootFactory : ItemFactory<Root>("usx") {

    override fun onInitialize() {
        register(BookIdentificationFactory)
        register(BookHeaderFactory)
        register(BookTitleFactory)
        register(BookIntroductionFactory)
        register(BookIntroductionTableFactory)
        register(BookIntroductionEndTitleFactory)
        register(BookChapterLabelFactory)
        register(ChapterStartFactory)
        register(ChapterEndFactory)
        register(ParagraphFactory)
        register(ListFactory)
        register(TableFactory)
        register(FootnoteFactory)
        register(CrossReferenceFactory)
        // register(Sidebar)
        register(TextFactory)
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) = Root(context, parent)
}
