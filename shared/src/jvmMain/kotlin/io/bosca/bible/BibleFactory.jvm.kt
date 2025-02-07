package io.bosca.bible

import io.bosca.bible.components.StyleRegistry
import io.bosca.bible.processor.BookProcessor
import io.bosca.bible.processor.MetadataProcessor
import io.bosca.bible.processor.StyleProcessor
import io.bosca.bible.processor.usx.Bible
import io.bosca.bible.processor.usx.Book
import io.bosca.bible.processor.usx.RootFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.zip.ZipFile

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object BibleFactory {

    actual suspend fun getBible(filename: String): IBible {
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

        val registry = StyleRegistry()
        val metadata = files["metadata.xml"]?.let { MetadataProcessor.process(it) } ?: error("missing metadata")
        val stylesheet = files["release/styles.xml"]?.let { StyleProcessor.process(it) }
        RootFactory.initialize()

        registry.register(stylesheet ?: emptyList())

        val processor = BookProcessor()
        val books = mutableListOf<Book>()
        for (name in metadata.publication.names) {
            val content = metadata.publication.contents[name.id] ?: error("missing content: ${name.id}")
            val file = files[content.file] ?: error("missing file: ${content.file}")
            val book = processor.process(name, content, file, registry)
            books.add(book)
        }

        return Bible(metadata, books, stylesheet ?: emptyList())
    }
}