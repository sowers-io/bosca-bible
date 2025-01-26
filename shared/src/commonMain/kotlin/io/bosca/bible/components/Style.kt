package io.bosca.bible.components

import kotlinx.serialization.Serializable

@Serializable
data class Size(
    val size: Int,
    val unit: SizeUnit
)

enum class SizeUnit {
    POINT,
    PERCENT,
    INCH,
}

enum class TextAlign {
    LEFT
}

enum class TextDecoration {
    UNDERLINE,
}

enum class FontWeight {
    NORMAL, BOLD, ITALIC
}

enum class Whitespace {
    NOWRAP
}

enum class VerticalAlign {
    TEXT_TOP
}

@Serializable
data class Margin(
    val top: Size?,
    val bottom: Size?,
    val left: Size?,
    val right: Size?,
)

@Serializable
data class Style(
    val id: String,
    val fontFamily: String?,
    val fontSize: Size?,
    val align: TextAlign?,
    val fontWeight: FontWeight?,
    val color: String?,
    val margin: Margin?,
    val whiteSpace: Whitespace?,
    val verticalAlign: VerticalAlign?,
    val textDecoration: TextDecoration?,
    val textIndent: Size?
)