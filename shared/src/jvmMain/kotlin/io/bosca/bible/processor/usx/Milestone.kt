package io.bosca.bible.processor.usx

import io.bosca.bible.components.IComponent
import io.bosca.bible.processor.ComponentContext
import io.bosca.bible.processor.Context
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.StringContext

class Milestone(context: Context, parent: Item?, attributes: Attributes) : AbstractItem(context, parent),
    CharItem,
    ParagraphItem,
    BookIntroductionItem,
    BookIntroductionEndTitleItem,
    ListCharItem {

    val style = attributes["STYLE"] ?: error("missing style")
    val sid = attributes["SID"] ?: error("missing sid")
    val eid = attributes["EID"] ?: error("missing eid")

    override val htmlClass = style
    override val htmlAttributes = emptyMap<String, String>()

    override fun toComponent(context: ComponentContext) = null
    override fun toHtml(context: HtmlContext) = context.render("milestone", this)
    override fun toString(context: StringContext) = ""
}

object MilestoneFactory : ItemFactory<Milestone>("ms") {

    override fun onInitialize() {
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) =
        Milestone(context, parent, attributes ?: error("missing attributes"))
}
