package io.bosca.bible

import io.bosca.bible.components.IComponent
import kotlinx.serialization.Serializable

@Serializable
data class Chapter(
    override val reference: Reference,
): IChapter {

    override fun get(reference: Reference): IComponent? {
        TODO("Not yet implemented")
    }
}