package io.bosca.bible.processor.usx

import io.bosca.bible.Reference
import io.bosca.bible.processor.Context
import io.bosca.bible.processor.Position
import io.bosca.bible.processor.StringContext

interface Item : Usx {

    val position: Position
}

abstract class AbstractItem(
    context: Context,
    parent: Usx?
) : Item {

    private val _reference = context.add(parent, this)

    override val reference: Reference? = _reference

    override val position: Position = context.position

    override val verse: String?
        get() = reference?.number

    override fun toString() = toString(StringContext.default)
}