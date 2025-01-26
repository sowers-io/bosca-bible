package io.bosca.bible.processor.usx

import io.bosca.bible.processor.Context

class EndIdFactoryFilter: ItemFactoryFilter {

    override fun supports(context: Context, attributes: Attributes?, progression: Int?): Boolean {
        if (attributes == null) return false
        if (attributes["EID"] == null) return false
        return true
    }
}