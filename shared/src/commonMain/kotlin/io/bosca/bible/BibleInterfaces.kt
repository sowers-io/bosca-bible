package io.bosca.bible

import io.bosca.bible.components.IComponent
import io.bosca.bible.components.IStyle

interface IBible {

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