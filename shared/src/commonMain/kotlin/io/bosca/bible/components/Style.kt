package io.bosca.bible.components

import kotlinx.serialization.Serializable

@Serializable
data class Size(
    val size: Float,
    val unit: SizeUnit
)

enum class SizeUnit {
    POINT,
    PERCENT,
    INCH,
}

enum class TextAlign {
    LEFT,
    CENTER,
    RIGHT
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

class StyleRegistry {

    private val styles = mutableMapOf<String, IStyle>()

    fun register(styles: List<IStyle>) {
        styles.associateByTo(this.styles) { it.id }
    }

    operator fun get(id: String) = styles[id]
}

interface IStyle {
    val id: String
    val fontFamily: String?
    val fontSize: Size?
    val align: TextAlign?
    val fontWeight: FontWeight?
    val color: String?
    val margin: Margin?
    val whiteSpace: Whitespace?
    val verticalAlign: VerticalAlign?
    val textDecoration: TextDecoration?
    val textIndent: Size?

    fun initialize(registry: StyleRegistry)
}

@Serializable
data class Style(
    override val id: String,
    override val fontFamily: String? = null,
    override val fontSize: Size? = null,
    override val align: TextAlign? = null,
    override val fontWeight: FontWeight? = null,
    override val color: String? = null,
    override val margin: Margin? = null,
    override val whiteSpace: Whitespace? = null,
    override val verticalAlign: VerticalAlign? = null,
    override val textDecoration: TextDecoration? = null,
    override val textIndent: Size? = null
) : IStyle {

    override fun initialize(registry: StyleRegistry) { }

    override fun toString(): String {
        return "Style(id='$id', fontFamily=$fontFamily, fontSize=$fontSize, align=$align, fontWeight=$fontWeight, color=$color, margin=$margin, whiteSpace=$whiteSpace, verticalAlign=$verticalAlign, textDecoration=$textDecoration, textIndent=$textIndent)"
    }
}

@Serializable
data class StyleReference(override val id: String) : IStyle {

    override var fontFamily: String? = null
        private set

    override var fontSize: Size? = null
        private set

    override var align: TextAlign? = null
        private set

    override var fontWeight: FontWeight? = null
        private set

    override var color: String? = null
        private set

    override var margin: Margin? = null
        private set

    override var whiteSpace: Whitespace? = null
        private set

    override var verticalAlign: VerticalAlign? = null
        private set

    override var textDecoration: TextDecoration? = null
        private set

    override var textIndent: Size? = null
        private set

    override fun initialize(registry: StyleRegistry) {
        for (id in id.split(" ")) {
            val style = registry[id] ?: continue
            style.fontFamily?.let { fontFamily = it }
            style.fontSize?.let { fontSize = it }
            style.fontWeight?.let { fontWeight = it }
            style.align?.let { align = it }
            style.color?.let { color = it }
            style.margin?.let { margin = it }
            style.verticalAlign?.let { verticalAlign = it }
            style.textDecoration?.let { textDecoration = it }
            style.textIndent?.let { textIndent = it }
        }
    }
}
