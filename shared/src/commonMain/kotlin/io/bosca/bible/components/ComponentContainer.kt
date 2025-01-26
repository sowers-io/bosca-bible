package io.bosca.bible.components

import kotlinx.serialization.Serializable

@Serializable
class ComponentContainer(
    val components: List<IComponent>,
    override val style: Style?
) : IComponent