package io.bosca.bible

import kotlinx.serialization.Serializable

@Serializable
data class Name(override val long: String, override val short: String, override val abbreviation: String) : IName {

}