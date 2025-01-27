package io.bosca.bible.components

import kotlinx.serialization.Serializable

@Serializable
class Break(override val style: IStyle? = null) : IComponent {
}