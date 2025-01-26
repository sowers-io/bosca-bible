package io.bosca.bible.processor.usx

import io.bosca.bible.processor.Context

class NegateFactoryFilter(private val filter: ItemFactoryFilter) : ItemFactoryFilter {

    override fun supports(context: Context, attributes: Attributes?, progression: Int?): Boolean {
        return !filter.supports(context, attributes, progression)
    }
}