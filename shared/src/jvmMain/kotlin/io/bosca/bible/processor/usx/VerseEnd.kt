package io.bosca.bible.processor.usx

import io.bosca.bible.processor.ComponentContext
import io.bosca.bible.processor.Context
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.StringContext

class VerseEnd(
    context: Context,
    parent: Item?,
    attributes: Attributes
) : AbstractItem(context, parent),
    ParagraphItem,
    ListItem,
    RowItem,
    TableContentItem {

    val eid: String = attributes["EID"] ?: error("missing eid")

    override val verse: String? = null
    override val htmlClass: String = ""
    override val htmlAttributes: Map<String, String> = emptyMap()

    override fun toComponent(context: ComponentContext) = io.bosca.bible.components.VerseEnd()
    override fun toHtml(context: HtmlContext) = ""
    override fun toString(context: StringContext) = ""
}

object VerseEndFactory : ItemFactory<VerseEnd>("verse", EndIdFactoryFilter()) {
    override fun onInitialize() {
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) =
        VerseEnd(context, parent, attributes ?: error("missing attributes"))
}
