package io.bosca.bible.processor.usx

import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.StringContext

interface Usx {

    val verse: String?

    val htmlClass: String
    val htmlAttributes: Map<String, String>

    fun toHtml(context: HtmlContext): String
    fun toString(context: StringContext): String
}
