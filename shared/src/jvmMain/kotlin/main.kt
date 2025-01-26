import io.bosca.bible.process
import io.bosca.bible.processor.HtmlContext
import io.bosca.bible.processor.usx.Bible

suspend fun main() {
    val bible = process("../asv.zip") as Bible

    val book = bible.booksByUsfm["GEN"] ?: error("missing book")
    val chapter = book.chaptersByUsfm["GEN.2"] ?: error("missing chapter")
    println(chapter.getVerses(book).joinToString("\n") { "${it.verse}. $it" })
    println(chapter.toHtml(HtmlContext(true, false, false, true)))
}