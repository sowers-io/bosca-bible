package io.bosca.bible.processor.usx

import io.bosca.bible.processor.Context


class Root(
    context: Context,
    parent: Usx?
) : ItemContainer<UsxItem>(context, parent), UsxItem {

    override val htmlClass: String = ""
}

object RootFactory : ItemFactory<Root>("usx") {

    override fun onInitialize() {
        register(BookIdentificationFactory)
        register(BookHeaderFactory)
        /*
    this.register(BookHeaderFactory.instance)
    this.register(BookTitleFactory.instance)
    this.register(BookIntroductionFactory.instance)
    this.register(BookIntroductionTableFactory.instance)
    this.register(BookIntroductionEndTitleFactory.instance)
    this.register(BookChapterLabelFactory.instance)
    this.register(ChapterStartFactory.instance)
    this.register(ChapterEndFactory.instance)
    this.register(ParagraphFactory.instance)
    this.register(ListFactory.instance)
    this.register(TableFactory.instance)
    this.register(FootnoteFactory.instance)
    this.register(CrossReferenceFactory.instance)
    // this.register(Sidebar)
    this.register(TextFactory.instance)
         */
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) = Root(context, parent)
}

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