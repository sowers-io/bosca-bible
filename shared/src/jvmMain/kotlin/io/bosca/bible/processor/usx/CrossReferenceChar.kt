package io.bosca.bible.processor.usx

import io.bosca.bible.CrossReferenceCharStyle
import io.bosca.bible.processor.Context
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.StringContext

//type CrossReferenceCharType = Char | CrossReferenceChar | Reference | Text

interface CrossReferenceCharItem : Item

class CrossReferenceChar(context: Context, parent: Item?, attributes: Attributes) :
    ItemContainer<CrossReferenceCharItem>(context, parent),
    RootItem,
    CrossReferenceItem,
    CrossReferenceCharItem {

    val style = CrossReferenceCharStyle.valueOf(attributes["STYLE"] ?: error("missing style"))

    override val htmlClass = style.toString()

    override fun toHtml(context: HtmlContext): String {
        if (!context.includeCrossReferences) return ""
        return super.toHtml(context)
    }

    override fun toString(context: StringContext): String {
        if (!context.includeCrossReferences) return ""
        return super.toString(context)
    }
}

object CrossReferenceCharFactory :
    ItemFactory<CrossReferenceChar>(
        "char",
        StyleFactoryFilter(CrossReferenceCharStyle.entries) { CrossReferenceCharStyle.valueOf(it) }) {

    override fun onInitialize() {
        register(CharFactory)
        register(CrossReferenceCharFactory)
        register(ReferenceFactory)
        register(TextFactory)
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) =
        CrossReferenceChar(context, parent, attributes ?: error("missing attributes"))
}
