package io.bosca.bible

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object BibleFactory {

    suspend fun getBible(filename: String): IBible
}