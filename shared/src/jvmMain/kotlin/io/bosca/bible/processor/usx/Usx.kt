package io.bosca.bible.processor.usx

import io.bosca.bible.processor.Context
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.StringContext
import org.xml.sax.Attributes

interface UsxItem : Item

interface Usx {

    val verse: String?

    val htmlClass: String
    val htmlAttributes: Map<String, String>

    fun toHtml(context: HtmlContext): String
    fun toString(context: StringContext): String
}
