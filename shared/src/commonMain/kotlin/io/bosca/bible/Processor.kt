package io.bosca.bible

import io.bosca.bible.components.IStyle

expect suspend fun process(filename: String): Pair<IBible, List<IStyle>>