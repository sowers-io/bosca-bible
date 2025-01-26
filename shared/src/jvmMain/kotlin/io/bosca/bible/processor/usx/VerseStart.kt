package io.bosca.bible.processor.usx

import io.bosca.bible.VerseStartStyle
import io.bosca.bible.processor.Context
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.StringContext


class VerseStart(
    context: Context,
    parent: Usx?,
    attributes: Attributes
) : AbstractItem(context, parent) {

    val style: VerseStartStyle = VerseStartStyle.valueOf(attributes.get("STYLE") ?: error("missing style"))
    val number: String = attributes.get("NUMBER") ?: error("missing number")
    val altNumber: String? = attributes.get("ALTNUMBER")?.takeIf { it.isNotEmpty() }
    val pubNumber: String? = attributes.get("PUBNUMBER")?.takeIf { it.isNotEmpty() }
    val sid: String = attributes.get("SID") ?: error("missing sid")

    override val htmlClass: String
        get() = style.name

    override val htmlAttributes: Map<String, String>
        get() = mapOf("data-verse" to number)

    override fun toHtml(context: HtmlContext): String {
        if (context.includeVerseNumbers) return context.render("span", this, this.number)
        return ""
    }

    override fun toString(context: StringContext): String {
        if (context.includeVerseNumbers) return "$number. "
        return ""
    }
}