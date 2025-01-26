package io.bosca.bible.processor.usx

import io.bosca.bible.processor.Context

class Figure(context: Context, parent: Item?, attributes: Attributes) : ItemContainer<Text>(context, parent),
    ParagraphItem,
    BookIntroductionItem {

    val style = attributes["STYLE"] ?: error("missing style")
    val alt = attributes["ALT"]
    val file = attributes["FILE"] ?: error("missing file")
    val size = attributes["SIZE"]
    val loc = attributes["LOC"]
    val copy = attributes["COPY"]
    val ref = attributes["REF"]

    override val htmlClass = style
}

object FigureFactory : ItemFactory<Figure>("figure") {

    override fun onInitialize() {

    }

    override fun create(context: Context, parent: Item?, attributes: Attributes?) =
        Figure(context, parent, attributes!!)
}
