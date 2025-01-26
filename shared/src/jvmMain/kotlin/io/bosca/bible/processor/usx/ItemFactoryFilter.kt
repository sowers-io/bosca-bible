package io.bosca.bible.processor.usx

import io.bosca.bible.processor.Context

interface ItemFactoryFilter {
    fun supports(context: Context, attributes: Attributes?, progression: Int?): Boolean
}