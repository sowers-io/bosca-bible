package io.bosca.bible.processor.usx

import io.bosca.bible.processor.Context
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.StringContext
import org.xml.sax.Attributes

class VerseEnd(
    context: Context,
    parent: Item?,
    attributes: Attributes
): AbstractItem(context, parent) {

    val eid: String = attributes.getValue("EID")

    override val verse: String? = null
    override val htmlClass: String = ""
    override val htmlAttributes: Map<String, String> = emptyMap()

    override fun toHtml(context: HtmlContext) = ""
    override fun toString(context: StringContext) = ""
}
