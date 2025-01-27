package io.bosca.bible.processor.usx

import io.bosca.bible.Reference
import io.bosca.bible.processor.ComponentContext
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.Position
import io.bosca.bible.processor.StringContext

class VerseItems(
    override val reference: Reference,
    val position: Position,
    verseStart: VerseStart
) : Usx {

    private val _items = mutableListOf<Item>(verseStart)

    override val verse: String = verseStart.number

    val items: kotlin.collections.List<Item>
        get() = _items

    fun add(item: Item) {
        _items.add(item)
    }

    override val htmlClass = "verses"
    override val htmlAttributes = emptyMap<String, String>()

    override fun toComponent(context: ComponentContext) = TODO()
    override fun toHtml(context: HtmlContext): String = context.render("div", this)
    override fun toString(context: StringContext): String {
        var verseContent = ""
        for (item in _items) {
            verseContent += item.toString(context)
        }
        return verseContent.trim()
    }
}