package io.bosca.bible.processor.usx

import io.bosca.bible.components.ComponentContainer
import io.bosca.bible.components.ContainerType
import io.bosca.bible.components.StyleReference
import io.bosca.bible.processor.ComponentContext
import io.bosca.bible.processor.Context
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.StringContext

abstract class ItemContainer<T : Item>(
    context: Context,
    parent: Usx?
) : AbstractItem(context, parent) {

    private val _items = mutableListOf<T>()

    val items: kotlin.collections.List<T>
        get() = _items

    override val htmlAttributes: Map<String, String>
        get() = emptyMap()

    open fun add(item: T) {
        _items.add(item)
    }

    override fun toComponent(context: ComponentContext) =
        ComponentContainer(
            ContainerType.DIV,
            items.mapNotNull { it.toComponent(context) },
            StyleReference(htmlClass)
        )

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