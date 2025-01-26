package io.bosca.bible.processor.usx


class Attributes(private val attributes: org.xml.sax.Attributes) {

    operator fun get(key: String): String? = attributes.getValue(key)
}