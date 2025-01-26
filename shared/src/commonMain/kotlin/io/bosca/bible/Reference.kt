package io.bosca.bible

import kotlinx.serialization.Serializable

@Serializable
data class Reference(val usfm: String) {

    val bookUsfm: String by lazy {
        this.usfm.split('.')[0]
    }

    val chapterUsfm: String by lazy {
        val parts = this.usfm.split('.')
        parts[0] + '.' + parts[1]
    }

    val chapter: String by lazy {
        usfm.split('.')[1]
    }

    val number: String by lazy {
        usfm.split('.').last()
    }

    val references: List<Reference> by lazy {
        usfm.split('+').map { Reference(it) }
    }

    operator fun plus(other: Reference): Reference {
        return Reference(usfm + '+' + other.usfm)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Reference

        return usfm == other.usfm
    }
    override fun hashCode() = usfm.hashCode()
    override fun toString() = usfm

    companion object {

        fun parse(bible: IBible, human: String): List<Reference> {
            val parts = human.split(',')
            val references = mutableListOf<Reference>()
            for (part in parts) {
                val reference = parseSingle(bible, part.trim())
                if (reference != null) {
                    references.add(reference)
                }
            }
            val joinedReferences = mutableMapOf<String, String>()
            for (reference in references) {
                var usfms = joinedReferences[reference.chapter]
                if (usfms == null) {
                    usfms = reference.usfm
                } else {
                    usfms += '+' + reference.usfm
                }
                joinedReferences[reference.chapter] = usfms
            }
            val finalReferences = mutableListOf<Reference>()
            for (usfm in joinedReferences.values) {
                finalReferences.add(Reference(usfm))
            }
            return finalReferences
        }

        private fun parseSingle(bible: IBible, human: String): Reference? {
            val humanLower = human.lowercase()
            var book: IBook? = null
            var nonBook: String? = null
            for (b in bible.books) {
                if (humanLower.indexOf(b.name.long.lowercase()) == 0) {
                    book = b
                    nonBook = humanLower.substring(b.name.long.length).trim()
                } else if (humanLower.indexOf(b.name.short.lowercase()) == 0) {
                    book = b
                    nonBook = humanLower.substring(b.name.short.length).trim()
                } else if (humanLower.indexOf(b.name.abbreviation.lowercase()) == 0) {
                    book = b
                    nonBook = humanLower.substring(b.name.abbreviation.length).trim()
                }
            }
            if (book == null || nonBook == null) {
                return null
            }
            if (nonBook.isEmpty()) {
                return Reference(book.reference.usfm)
            }
            val numberParts = nonBook.split(':').toMutableList()
            val chapter = book.chapters.find({ it.reference.number.lowercase() === numberParts[0].lowercase() })
            if (chapter == null) {
                return book.reference
            }
            if (numberParts.size == 1) {
                return chapter.reference
            }
            if (numberParts[1].contains('–')) {
                numberParts[1] = numberParts[1].replace('–', '-')
            }
            if (numberParts[1].contains('-')) {
                val rangeParts = numberParts[1].split('-')
                if (rangeParts.size == 2) {
                    val start = rangeParts[0].toInt()
                    val end = rangeParts[1].toInt()
                    val usfms = mutableListOf<String>()
                    var i = start
                    while (i <= end) {
                        usfms.add("${chapter.reference.usfm}.$i")
                        i++
                    }
                    return Reference(usfms.joinToString("+"))
                } else {
                    numberParts[1] = rangeParts[0]
                }
            }
            return Reference("${chapter.reference.usfm}.${numberParts[1]}")
        }
    }
}