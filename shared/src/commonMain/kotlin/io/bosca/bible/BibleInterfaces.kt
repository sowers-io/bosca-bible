package io.bosca.bible

interface IBible {

    val books: List<IBook>

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
}