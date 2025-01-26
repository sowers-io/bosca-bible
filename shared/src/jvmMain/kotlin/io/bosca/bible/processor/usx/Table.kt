package io.bosca.bible.processor.usx

import io.bosca.bible.processor.Context
import io.bosca.bible.processor.HtmlContext

class Table(
    context: Context,
    parent: Item?,
    attributes: Attributes
) : ItemContainer<Row>(context, parent),
    RootItem {

    val vid: String = attributes["VID"] ?: error("missing vid")

    override val htmlClass = ""

    override fun toHtml(context: HtmlContext) = context.render("table", this)
}

interface RowItem : Item

class Row(
    context: Context,
    parent: Item?,
    attributes: Attributes
) : ItemContainer<RowItem>(context, parent) {

    val style = attributes["STYLE"] ?: error("missing style")

    override val htmlClass = style
    override fun toHtml(context: HtmlContext) = context.render("tr", this)
}

// type TableContentType = Footnote | CrossReference | Char | Milestone | Figure | Verse | Break | Text
interface TableContentItem : Item

class TableContent(
    context: Context,
    parent: Item?,
    attributes: Attributes
) : ItemContainer<TableContentItem>(context, parent), RowItem {
    val style: String = attributes["STYLE"] ?: error("missing style")
    val align: String = attributes["ALIGN"] ?: error("missing align")
    val colspan: String? = attributes["COLSPAN"]

    override val htmlClass = style

    override fun toHtml(context: HtmlContext) = context.render("td", this)
}

object TableFactory : ItemFactory<Table>("table") {

    override fun onInitialize() {
        register(RowFactory)
        register(VerseStartFactory)
        register(VerseEndFactory)
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) = Table(context, parent, attributes!!)
}

object RowFactory : ItemFactory<Row>("row") {

    override fun onInitialize() {
        register(TableContentFactory)
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) = Row(context, parent, attributes!!)
}

object TableContentFactory : ItemFactory<TableContent>("cell") {

    override fun onInitialize() {
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
        TableContent(context, parent, attributes!!)
}
