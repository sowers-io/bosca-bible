package io.bosca.bible.processor

import io.bosca.bible.processor.usx.Metadata

object MetadataProcessor {

    fun process(data: ByteArray): Metadata {
        @Suppress("UNCHECKED_CAST")
        return (Metadata(XMLProcessor.process(data)["DBLMetadata"] as Map<String, Any>))
    }
}