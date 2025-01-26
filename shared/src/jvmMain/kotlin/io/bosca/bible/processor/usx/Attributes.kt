package io.bosca.bible.processor.usx


class Attributes(private val attributes: Map<String, String>) {

    operator fun get(key: String): String? = attributes[key.lowercase()]
}