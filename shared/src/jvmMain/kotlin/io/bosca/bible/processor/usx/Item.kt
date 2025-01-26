package io.bosca.bible.processor.usx

import io.bosca.bible.processor.Context
import io.bosca.bible.processor.Position

interface Item : Usx {

    val position: Position
}

abstract class AbstractItem(
    context: Context,
    parent: Usx?
) : Item {

    override val position: Position = context.position

    override val verse: String? = context.add(parent, this)
}