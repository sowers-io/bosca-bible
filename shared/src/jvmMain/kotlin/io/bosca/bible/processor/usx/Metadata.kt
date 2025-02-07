package io.bosca.bible.processor.usx

import io.bosca.bible.*
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

class MetadataSystemId(private val systemId: List<Map<String, Any>>): IBibleSystem {

    override val id: String
        get() {
            for (id in systemId) {
                if (id["type"] == "paratext") {
                    return id["id"] as String
                }
            }
            error("unknown id")
        }
}

class MetadataIdentification(private val identification: Map<String, Any>): IBibleIdentification {

    @Suppress("UNCHECKED_CAST")
    override val system = MetadataSystemId(identification["systemId"] as List<Map<String, Any>>)

    override val name: String
        get() = identification["name"] as String

    override val nameLocal: String
        get() = identification["nameLocal"] as String

    override val description: String
        get() = identification["description"] as String

    override val abbreviation: String
        get() = identification["abbreviation"] as String

    override val abbreviationLocal: String
        get() = identification["abbreviationLocal"] as String
}

class MetadataLanguage(private val language: Map<String, Any>): IBibleLanguage {

    override val iso: String
        get() = language["iso"] as String

    override val name: String
        get() = language["name"] as String

    override val nameLocal: String
        get() = language["nameLocal"] as String

    override val script: String
        get() = language["script"] as String

    override val scriptCode: String
        get() = language["scriptCode"] as String

    override val scriptDirection: String
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

class Metadata(private val metadata: Map<String, Any>): IBibleMetadata {

    @Suppress("UNCHECKED_CAST")
    override val identification = MetadataIdentification(metadata["identification"] as Map<String, Any>)

    @Suppress("UNCHECKED_CAST")
    override val language = MetadataLanguage(metadata["language"] as Map<String, Any>)

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