package io.bosca.bible

import kotlinx.serialization.Serializable

@Serializable
data class Chapter(
    override val reference: Reference,
): IChapter {
}