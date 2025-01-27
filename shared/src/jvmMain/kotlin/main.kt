import io.bosca.bible.Reference
import io.bosca.bible.components.Style
import io.bosca.bible.components.StyleRegistry
import io.bosca.bible.components.initializeStyles
import io.bosca.bible.process
import io.bosca.bible.processor.ComponentContext
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.usx.Bible
import io.bosca.bible.processor.usx.Book
import io.bosca.bible.processor.usx.Chapter

suspend fun main() {
    val (bible, styles) = process("../asv.zip")

    val reference = Reference("GEN.2")
    val book = bible[reference] as Book? ?: error("missing book")
    val chapter = book[reference] as Chapter? ?: error("missing chapter")
    println(chapter.getVerses(book).joinToString("\n") { "${it.verse}. $it" })
    println(chapter.toHtml(HtmlContext(
        pretty = true,
        includeFootNotes = false,
        includeCrossReferences = false,
        includeVerseNumbers = true
    )))

    val registry = StyleRegistry()
    registry.register(styles)

    val components = chapter.toComponent(ComponentContext())
    components.initializeStyles(registry)

    println(components)
}