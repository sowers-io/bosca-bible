package io.bosca.bible

import io.bosca.bible.components.IStyle
import kotlinx.serialization.Serializable

@Serializable
class BibleSystem(override val id: String) : IBibleSystem

@Serializable
class BibleLanguage(
    override val iso: String,
    override val name: String,
    override val nameLocal: String,
    override val script: String,
    override val scriptCode: String,
    override val scriptDirection: String
) : IBibleLanguage

@Serializable
class BibleIdentification(
    override val system: BibleSystem,
    override val name: String,
    override val nameLocal: String,
    override val description: String,
    override val abbreviation: String,
    override val abbreviationLocal: String
) : IBibleIdentification

@Serializable
class BibleMetadata(
    override val identification: BibleIdentification,
    override val language: BibleLanguage
) : IBibleMetadata

@Serializable
class Bible(
    override val metadata: BibleMetadata,
    override val books: List<Book>,
    override val styles: List<IStyle>
) : IBible {

    private val booksByUsfm = books.associateBy { it.reference.bookUsfm }

    override fun get(reference: Reference) = booksByUsfm[reference.bookUsfm]
}
