package io.bosca.bible.processor.usx

import io.bosca.bible.processor.Context

abstract class ItemFactory<T: Item>(val tagName: String, private val filter: ItemFactoryFilter? = null) {

    private val factories = mutableMapOf<String, MutableList<ItemFactory<*>>>()
    private var initialized = false

    fun initialize() {
        if (initialized) return
        initialized = true
        onInitialize()
        for (tag in factories) {
            for (factory in tag.value) {
                factory.initialize()
            }
        }
    }

    protected fun register(factory: ItemFactory<*>) {
        val factories = this.factories[factory.tagName]
        if (factories == null) {
            this.factories[factory.tagName] = mutableListOf(factory)
        } else {
            factories.add(factory)
        }
    }

    fun supports(context: Context, attributes: Attributes?, progression: Int?): Boolean =
        filter?.supports(context, attributes, progression) ?: true

    protected abstract fun onInitialize()

    abstract fun create(context: Context, parent: Item?, attributes: Attributes?): T

    fun findChildFactory(context: Context, parent: Node, tag: Tag): ItemFactory<*> {
        val factories = this.factories[tag.name.lowercase()] ?: error("unsupported tag: ${tag.name} in ${this.tagName}")
        val supported = factories.filter { context.supports(it, parent, tag) }
        if (supported.isEmpty()) {
            error("zero supported items")
        } else if (supported.size > 1) {
            error("multiple supported items")
        }
        return supported.first()
    }
}