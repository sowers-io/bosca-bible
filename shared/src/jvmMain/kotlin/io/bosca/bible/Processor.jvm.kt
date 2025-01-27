package io.bosca.bible

import io.bosca.bible.components.IStyle
import io.bosca.bible.processor.BookProcessor
import io.bosca.bible.processor.MetadataProcessor
import io.bosca.bible.processor.StyleProcessor
import io.bosca.bible.processor.XMLProcessor
import io.bosca.bible.processor.usx.Bible
import io.bosca.bible.processor.usx.Book
import io.bosca.bible.processor.usx.Metadata
import io.bosca.bible.processor.usx.RootFactory
import java.util.zip.ZipFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

actual suspend fun process(filename: String): Pair<IBible, List<IStyle>> {
    val files = mutableMapOf<String, ByteArray>()

    withContext(Dispatchers.IO) {
        ZipFile(filename).use { file ->
            file.entries().asSequence().forEach { entry ->
                file.getInputStream(entry).use { input ->
                    files[entry.name] = input.readAllBytes()
                }
            }
        }
    }

    val metadata = files["metadata.xml"]?.let { MetadataProcessor.process(it) } ?: error("missing metadata")
    val stylesheet = files["release/styles.xml"]?.let { StyleProcessor.process(it) }

    RootFactory.initialize()

    val processor = BookProcessor()
    val books = mutableListOf<Book>()
    for (name in metadata.publication.names) {
        val content = metadata.publication.contents[name.id] ?: error("missing content: ${name.id}")
        val file = files[content.file] ?: error("missing file: ${content.file}")
        val book = processor.process(name, content, file)
        books.add(book)
    }

    return Pair(Bible(books), stylesheet ?: emptyList())
}