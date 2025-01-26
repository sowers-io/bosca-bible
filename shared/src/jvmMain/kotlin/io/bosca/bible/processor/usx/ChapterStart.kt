package io.bosca.bible.processor.usx

import io.bosca.bible.processor.Context
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.StringContext
import org.xml.sax.Attributes

class ChapterStart(
    context: Context,
    parent: Usx?,
    attributes: Attributes
) : AbstractItem(context, parent), UsxItem {

    val number: String = attributes.getValue("NUMBER")
    val altNumber: String? = attributes.getValue("ALTNUMBER")?.takeIf { it.isNotEmpty() }
    val pubNumber: String? = attributes.getValue("PUBNUMBER")?.takeIf { it.isNotEmpty() }
    val sid: String = attributes.getValue("SID")

    override val htmlClass: String = ""
    override val htmlAttributes = emptyMap<String, String>()

    override fun toHtml(context: HtmlContext) = ""
    override fun toString(context: StringContext) = ""
}
