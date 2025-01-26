package io.bosca.bible.processor.usx

data class Node(val factory: ItemFactory<*>, val item: Item, val position: Int)