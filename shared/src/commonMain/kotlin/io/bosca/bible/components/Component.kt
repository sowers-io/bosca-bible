package io.bosca.bible.components

interface IComponent {

    val style: IStyle?
}

fun IComponent.initializeStyles(registry: StyleRegistry) {
    style?.initialize(registry)
    if (this is ComponentContainer) {
        components.forEach { it.initializeStyles(registry) }
    }
}