package io.bosca.bible.processor.usx

import io.bosca.bible.processor.Context
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.StringContext

abstract class ItemContainer<T : Item>(
    context: Context,
    parent: Usx?
) : AbstractItem(context, parent) {

    private val _items = mutableListOf<T>()
    override val verse: String? = context.add(parent, this)

    val items: kotlin.collections.List<T>
        get() = _items

    override val htmlAttributes: Map<String, String>
        get() = emptyMap()

    open fun add(item: T) {
        _items.add(item)
    }

    override fun toHtml(context: HtmlContext) = context.render("div", this)

    override fun toString(context: StringContext): String {
        var verseContent = ""
        for (item in items) {
            verseContent += item.toString(context)
        }
        return verseContent.trim()
    }

    override fun toString(): String = toString(StringContext.default)
}