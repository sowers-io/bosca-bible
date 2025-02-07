package io.bosca.bible

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import io.bosca.bible.components.*
import kotlin.math.roundToInt

class Renderer(
    private val measurer: TextMeasurer,
    private val rootStyle: TextStyle,
) {

    private val layouts = mutableListOf<TextLayoutResult>()

    fun measure(size: Size, component: IComponent? = null) {
        layouts.clear()
        measureInternal(size, component ?: return)
    }

    private fun measureInternal(size: Size, component: IComponent) {
        when (component) {
            is ComponentContainer -> {
                for (child in component.components) {
                    measureInternal(size, child)
                }
            }
            is VerseStart -> {
            }
            is VerseEnd -> {
            }
            is Break -> {
            }
            is Text -> {
                layouts.add(measurer.measure(
                    text = component.text,
                    style = rootStyle,
                    constraints = Constraints.fixed(size.width.roundToInt(), size.height.roundToInt()),
                    overflow = TextOverflow.Visible,
                ))
            }
        }
    }

    fun render(scope: DrawScope) {
        for (layout in layouts) {
            scope.drawText(layout)
        }
    }
}