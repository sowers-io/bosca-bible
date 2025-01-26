package io.bosca.bible.processor.usx

import io.bosca.bible.CrossReferenceStyle
import io.bosca.bible.processor.Context
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.StringContext

interface CrossReferenceItem : Item

class CrossReference(context: Context, parent: Item?, attributes: Attributes) :
    ItemContainer<CrossReferenceItem>(context, parent),
    RootItem,
    ParagraphItem,
    BookIntroductionItem,
    BookIntroductionEndTitleItem {

    val style = CrossReferenceStyle.valueOf(attributes["STYLE"] ?: error("missing style"))
    val caller = attributes["CALLER"] ?: error("missing caller")

    override val htmlClass = style.toString()
    override val htmlAttributes = mapOf("data-caller" to caller) + super.htmlAttributes

    override fun toHtml(context: HtmlContext): String {
        if (!context.includeCrossReferences) return ""
        return super.toHtml(context)
    }

    override fun toString(context: StringContext): String {
        if (!context.includeCrossReferences) return ""
        return super.toString(context)
    }
}

object CrossReferenceFactory : ItemFactory<CrossReference>(
    "note",
    StyleFactoryFilter(CrossReferenceStyle.entries) { CrossReferenceStyle.valueOf(it) }) {

    override fun onInitialize() {
        register(CrossReferenceCharFactory)
        register(TextFactory)
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) =
        CrossReference(context, parent, attributes ?: error("missing attributes"))
}
