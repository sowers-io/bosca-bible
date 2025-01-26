package io.bosca.bible.processor

import io.bosca.bible.processor.usx.ItemContainer
import io.bosca.bible.processor.usx.Usx
import io.bosca.bible.processor.usx.VerseItems

class HtmlContext(
    val pretty: Boolean,
    val includeFootNotes: Boolean,
    val includeCrossReferences: Boolean,
    val includeVerseNumbers: Boolean,
    private var indent: Int = 0
) {

    fun addIndent() {
        this.indent += 2
    }

    fun removeIndent() {
        this.indent -= 2
    }

    fun render(tag: String, item: Usx, text: String? = null): String {
        var html = ""
        if (this.pretty) html += " ".repeat(this.indent)
        html += "<$tag"
        val attrs = item.htmlAttributes.toMutableMap()
        if (item.htmlClass != "") attrs["class"] = item.htmlClass
        for ((key, value) in attrs.entries) {
            html += " $key=\"$value\""
        }
        html += ">"
        if (this.pretty) html += "\n"
        this.addIndent()
        var childHtml = ""
        if (text != null) {
            if (this.pretty) html += " ".repeat(this.indent)
            childHtml += text
        } else if (item is ItemContainer<*>) {
            for (child in item.items) {
                childHtml += child.toHtml(this)
            }
        } else if (item is VerseItems) {
            for (child in item.items) {
                childHtml += child.toHtml(this)
            }
        } else {
            childHtml += item.toHtml(this)
        }
        html += childHtml
        if (this.pretty && !html.endsWith("\n")) html += "\n"
        this.removeIndent()
        if (this.pretty) html += " ".repeat(this.indent)
        html += "</$tag>"
        if (this.pretty) html += "\n"
        return html
    }
}