package io.bosca.bible.processor.usx

import io.bosca.bible.IName
import kotlin.collections.List

class ManifestName(private val name: Map<String, Any>): IName {

    val usfm: String
        get() = id.split("-").last().uppercase()

    val id: String
        get() = name["id"] as String

    override val abbreviation: String
        get() = name["abbr"] as String

    override val short: String
        get() = name["short"] as String

    override val long: String
        get() = name["long"] as String
}

class MetadataSystemId(private val systemId: List<Map<String, Any>>) {

    val id: String
        get() {
            for (id in systemId) {
                if (id["type"] == "paratext") {
                    return id["id"] as String
                }
            }
            error("unknown id")
        }
}

class MetadataIdentification(private val identification: Map<String, Any>) {

    @Suppress("UNCHECKED_CAST")
    val systemId = MetadataSystemId(identification["systemId"] as List<Map<String, Any>>)

    val name: String
        get() = identification["name"] as String

    val nameLocal: String
        get() = identification["nameLocal"] as String

    val description: String
        get() = identification["description"] as String

    val abbreviation: String
        get() = identification["abbreviation"] as String

    val abbreviationLocal: String
        get() = identification["abbreviationLocal"] as String
}

class MetadataLanguage(private val language: Map<String, Any>) {

    val iso: String
        get() = language["iso"] as String

    val name: String
        get() = language["name"] as String

    val nameLocal: String
        get() = language["nameLocal"] as String

    val script: String
        get() = language["script"] as String

    val scriptCode: String
        get() = language["scriptCode"] as String

    val scriptDirection: String
        get() = language["scriptDirection"] as String
}

class Publication(val names: List<ManifestName>, private val publication: Map<String, Any>) {

    val contents = mutableMapOf<String, PublicationContent>()

    init {
        @Suppress("UNCHECKED_CAST")
        val structure = publication["structure"] as Map<String, Any>
        @Suppress("UNCHECKED_CAST")
        val content = structure["content"] as List<Map<String, Any>>
        for (c in content) {
            val pub = PublicationContent(c)
            contents[pub.id] = pub
        }
    }
}

class PublicationContent(private val content: Map<String, Any>) {

    val usfm: String
        get() = content["role"] as String

    val id: String
        get() = content["name"] as String

    val file: String
        get() = content["src"] as String
}

class Metadata(private val metadata: Map<String, Any>) {

    @Suppress("UNCHECKED_CAST")
    val identification = MetadataIdentification(metadata["identification"] as Map<String, Any>)

    @Suppress("UNCHECKED_CAST")
    val language = MetadataLanguage(metadata["language"] as Map<String, Any>)

    val publication: Publication = metadata.let {
        @Suppress("UNCHECKED_CAST")
        val metadataNames = (metadata["names"] as Map<String, Any>).let {
            it["name"]
        } as List<Map<String, Any>>
        val names = mutableListOf<ManifestName>()
        for (name in metadataNames) {
            names.add(ManifestName(name))
        }
        @Suppress("UNCHECKED_CAST")
        val publications = metadata["publications"] as Map<String, Any>
        @Suppress("UNCHECKED_CAST")
        val publication = publications["publication"] as Map<String, Any>
        Publication(names, publication)
    }
}