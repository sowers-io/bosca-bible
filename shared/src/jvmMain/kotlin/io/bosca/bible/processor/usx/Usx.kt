package io.bosca.bible.processor.usx

import io.bosca.bible.components.IComponent
import io.bosca.bible.processor.ComponentContext
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.StringContext

interface Usx {

    val verse: String?

    val htmlClass: String
    val htmlAttributes: Map<String, String>

    fun toComponent(context: ComponentContext): IComponent
    fun toHtml(context: HtmlContext): String
    fun toString(context: StringContext): String
}
