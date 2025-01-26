package io.bosca.bible.processor.usx

import io.bosca.bible.processor.Context

class StyleFactoryFilter<T>(private val styles: kotlin.collections.List<T>, private val factory: (String) -> T): ItemFactoryFilter {

    override fun supports(context: Context, attributes: Attributes?, progression: Int?): Boolean {
        val style = try {
            factory(attributes?.get("STYLE") ?: return false)
        } catch (e: IllegalArgumentException) {
            return false
        }
        if (progression != null) {
            return styles[progression] == style
        }
        return styles.contains(style)
    }
}
