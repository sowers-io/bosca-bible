package io.bosca.bible.processor.usx

import io.bosca.bible.BookIdentificationCode
import io.bosca.bible.processor.Context

class CodeFactoryFilter(private val styles: Set<BookIdentificationCode>): ItemFactoryFilter {

    override fun supports(context: Context, attributes: Attributes?, progression: Int?): Boolean {
        if (attributes == null) return false
        attributes["CODE"]?.let { code ->
            return styles.contains(BookIdentificationCode.valueOf(code))
        }
        return false
    }
}