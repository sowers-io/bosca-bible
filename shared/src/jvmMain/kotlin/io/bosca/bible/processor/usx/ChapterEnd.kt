package io.bosca.bible.processor.usx

import io.bosca.bible.processor.Context
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.StringContext
import org.xml.sax.Attributes

class ChapterEnd(
    context: Context,
    parent: Item?,
    attributes: Attributes
) : AbstractItem(context, parent), ChapterItem, UsxItem {

    val eid: String = attributes.getValue("EID")

    override val htmlClass: String = ""
    override val htmlAttributes: Map<String, String> = emptyMap()

    override fun toHtml(context: HtmlContext): String = ""
    override fun toString(context: StringContext): String = ""
}
