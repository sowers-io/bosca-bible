package io.bosca.bible.processor.usx

import io.bosca.bible.Reference
import io.bosca.bible.VerseStartStyle
import io.bosca.bible.processor.ComponentContext
import io.bosca.bible.processor.Context
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.StringContext


class VerseStart(
    context: Context,
    parent: Usx?,
    attributes: Attributes,
) : AbstractItem(context, parent),
    ParagraphItem,
    ListItem,
    RowItem,
    TableContentItem {

    val style: VerseStartStyle = VerseStartStyle.valueOf(attributes["STYLE"] ?: error("missing style"))
    val number: String = attributes["NUMBER"] ?: error("missing number")
    val altNumber: String? = attributes["ALTNUMBER"]?.takeIf { it.isNotEmpty() }
    val pubNumber: String? = attributes["PUBNUMBER"]?.takeIf { it.isNotEmpty() }
    val sid: String = attributes["SID"] ?: error("missing sid")

    override val reference = Reference("${context.currentChapterReference.usfm}.$number")

    override val htmlClass: String
        get() = style.name

    override val htmlAttributes: Map<String, String>
        get() = mapOf(
            "data-usfm" to reference.usfm,
            "data-verse" to (verse ?: error("missing verse"))
        )

    override fun toComponent(context: ComponentContext) =
        io.bosca.bible.components.VerseStart(reference ?: error("missing reference"))

    override fun toHtml(context: HtmlContext): String {
        if (context.includeVerseNumbers) return context.render("span", this, this.number)
        return ""
    }

    override fun toString(context: StringContext): String {
        if (context.includeVerseNumbers) return "$number. "
        return ""
    }
}

object VerseStartFactory : ItemFactory<VerseStart>(
    "verse",
    StyleFactoryFilter(VerseStartStyle.entries.toList()) { VerseStartStyle.valueOf(it) }
) {

    override fun onInitialize() {}
    override fun create(context: Context, parent: Item?, attributes: Attributes?) =
        VerseStart(context, parent, attributes ?: error("missing attributes"))
}
