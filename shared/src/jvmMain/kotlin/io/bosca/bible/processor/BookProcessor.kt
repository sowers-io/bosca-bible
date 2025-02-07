package io.bosca.bible.processor

import io.bosca.bible.components.StyleRegistry
import io.bosca.bible.processor.usx.*
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamReader

class BookProcessor {

    fun process(name: ManifestName, content: PublicationContent, data: ByteArray, registry: StyleRegistry): Book {
        val book = Book(name, content, String(data, Charsets.UTF_8))
        val context = Context(book, registry)
        val input = data.inputStream()
        val factory = XMLInputFactory.newInstance()
        val reader = factory.createXMLStreamReader(input)
        while (reader.hasNext()) {
            when (reader.next()) {
                XMLStreamReader.START_ELEMENT -> {
                    val position = reader.location.characterOffset
                    val attrs = mutableMapOf<String, String>()
                    for (i in 0 until reader.attributeCount) {
                        val attr = reader.getAttributeName(i)
                        val value = reader.getAttributeValue(i)
                        attrs[attr.localPart.lowercase()] = value
                    }
                    context.push(Tag(reader.localName, Attributes(attrs)), position)
                }
                XMLStreamReader.CHARACTERS -> {
                    val position = reader.location.characterOffset
                    context.addText(reader.text, position)
                }
                XMLStreamReader.END_ELEMENT -> {
                    val position = reader.location.characterOffset
                    context.pop(position)
                }
            }
        }
        return book
    }
}