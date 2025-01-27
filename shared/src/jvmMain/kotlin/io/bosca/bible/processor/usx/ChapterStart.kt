package io.bosca.bible.processor.usx

import io.bosca.bible.processor.ComponentContext
import io.bosca.bible.processor.Context
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.StringContext

class ChapterStart(
    context: Context,
    parent: Usx?,
    attributes: Attributes
) : AbstractItem(context, parent),
    RootItem {

    val number: String = attributes["NUMBER"] ?: error("missing number")
    val altNumber: String? = attributes["ALTNUMBER"]
    val pubNumber: String? = attributes["PUBNUMBER"]
    val sid: String = attributes["SID"] ?: error("missing sid")

    override val htmlClass: String = ""
    override val htmlAttributes = emptyMap<String, String>()

    override fun toComponent(context: ComponentContext) = null
    override fun toHtml(context: HtmlContext) = ""
    override fun toString(context: StringContext) = ""
}

object ChapterStartFactory : ItemFactory<ChapterStart>("chapter", NegateFactoryFilter(EndIdFactoryFilter())) {

    override fun onInitialize() {
    }

    override fun create(
        context: Context,
        parent: Item?,
        attributes: Attributes?
    ) = ChapterStart(context, parent, attributes ?: error("missing attributes"))
}
