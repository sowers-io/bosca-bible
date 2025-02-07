package io.bosca.bible

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import io.bosca.bible.components.*
import kotlin.math.roundToInt

fun IComponent.toAnnotatedString(density: Density, fontSize: TextUnit): AnnotatedString {
    val component = this
    return buildAnnotatedString {
        when (component) {
            is ComponentContainer -> {
                var style = -1
                if (component.type == ContainerType.PARAGRAPH) {
                    style = pushStyle(
                        ParagraphStyle(
                            textIndent = component.style?.textIndent?.let {
                                val size = when (it.unit) {
                                    SizeUnit.INCH -> {
                                        it.size * density.density * 3 * fontSize.value
                                    }

                                    SizeUnit.POINT -> {
                                        it.size * 1.333f * fontSize.value
                                    }

                                    else -> TODO()
                                }
                                TextIndent(firstLine = size.sp)
                            }
                        ))
                }
                for (child in component.components) {
                    append(child.toAnnotatedString(density, fontSize))
                }
                if (style != -1) {
                    pop(style)
                }
            }

            is VerseStart -> {
                append(buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Gray,
                            fontFeatureSettings = "sups",
                        )
                    ) {
                        append(component.reference.number)
                        append("\u00A0")
                    }
                })
            }

            is VerseEnd -> {

            }

            is Break -> {
                append("\n")
            }

            is Text -> {
                if (!(component.text.contains("\n") && component.text.trim() == "")) {
                    append(component.text)
                }
            }

            else -> error("Unknown component type: ${component::class.simpleName}")
        }
    }
}