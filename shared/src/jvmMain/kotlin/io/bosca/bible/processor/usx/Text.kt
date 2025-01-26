package io.bosca.bible.processor.usx

import io.bosca.bible.processor.Context
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.StringContext


class Text(
    context: Context,
    parent: Usx?
): AbstractItem(context, parent) {

    var text = ""

    override val htmlClass = "verse"

    override val htmlAttributes: Map<String, String>
        get() {
            if (verse == null) return emptyMap()
            return mapOf("data-verse" to verse)
        }

    override fun toHtml(context: HtmlContext): String {
        if (text.trim().isEmpty()) return ""
        return context.render("span", this, text)
    }

    override fun toString(context: StringContext): String {
        if (!context.includeNewLines) return text.replace(Regex("\r?\n"), "")
        return text
    }
}


object TextFactory: ItemFactory<Text>("#text") {

    override fun onInitialize() { }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) = Text(context, parent as? Usx)
}
