package io.bosca.bible.processor.usx

import io.bosca.bible.processor.Context

class Reference(context: Context, parent: Item?, attributes: Attributes) : ItemContainer<Text>(context, parent),
    CharItem,
    ParagraphItem,
    BookIntroductionItem,
    FootnoteCharItem,
    ListCharItem {

    val loc = attributes["LOC"].toString()

    override val htmlClass: String = ""
}

object ReferenceFactory : ItemFactory<Reference>("ref") {
    override fun onInitialize() {
        register(TextFactory)
    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) =
        Reference(context, parent, attributes!!)
}
