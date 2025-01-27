package io.bosca.bible.components

import io.bosca.bible.Reference
import kotlinx.serialization.Serializable

@Serializable
class VerseStart(
    val reference: Reference,
    override val style: IStyle? = null
) : IComponent {

    override fun toString(): String {
        return "VerseStart(reference=$reference)"
    }
}