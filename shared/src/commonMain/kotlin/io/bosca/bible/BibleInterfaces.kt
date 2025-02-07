package io.bosca.bible

import io.bosca.bible.components.IComponent
import io.bosca.bible.components.IStyle

interface IBibleSystem {
    val id: String
}

interface IBibleIdentification {

    val system: IBibleSystem
    val name: String
    val nameLocal: String
    val description: String
    val abbreviation: String
    val abbreviationLocal: String
}

interface IBibleLanguage {

    val iso: String
    val name: String
    val nameLocal: String
    val script: String
    val scriptCode: String
    val scriptDirection: String
}

interface IBibleMetadata {

    val identification: IBibleIdentification
    val language: IBibleLanguage
}

interface IBible {

    val metadata: IBibleMetadata
    val books: List<IBook>
    val styles: List<IStyle>

    operator fun get(reference: Reference): IBook?
}

interface IName {

    val short: String
    val long: String
    val abbreviation: String
}

interface IBook {

    val name: IName
    val reference: Reference
    val chapters: List<IChapter>

    operator fun get(reference: Reference): IChapter?
}

interface IChapter {

    val reference: Reference

    operator fun get(reference: Reference): IComponent?
}