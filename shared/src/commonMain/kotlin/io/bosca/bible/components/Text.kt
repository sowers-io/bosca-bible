package io.bosca.bible.components

import kotlinx.serialization.Serializable

@Serializable
class Text(
    val text: String,
    override val style: Style?,
) : IComponent