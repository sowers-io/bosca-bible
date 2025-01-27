package io.bosca.bible.components

import kotlinx.serialization.Serializable

enum class ContainerType {
    DIV,
    SPAN,
    PARAGRAPH,
    TABLE,
    ROW,
    COLUMN,
}

@Serializable
class ComponentContainer(
    val type: ContainerType,
    val components: List<IComponent>,
    override val style: IStyle?
) : IComponent {

    override fun toString(): String {
        return "ComponentContainer(type=$type, components=$components)"
    }
}