package io.bosca.bible

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import io.bosca.bible.components.IComponent
import io.bosca.bible.components.Text
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
//        var component by remember { mutableStateOf<IComponent?>(null) }
//        val measurer = rememberTextMeasurer()
//        val rootStyle = LocalTextStyle.current
//        val renderer = remember { Renderer(measurer, rootStyle) }
//
//        LaunchedEffect(Unit) {
//            val bible = BibleFactory.getBible("../asv.zip")
//            val reference = Reference("GEN.2")
//            val book = bible[reference] ?: error("Book not found")
//            val chapter = book[reference]
//            component = chapter?.get(reference)
//        }
//
//        Box(
//            Modifier.fillMaxSize().drawWithCache {
//                renderer.measure(size, component)
//                onDrawWithContent {
//                    drawContent()
//                    renderer.render(this)
//                }
//            }
//        )

        val density = LocalDensity.current
        val style = LocalTextStyle.current
        var component by remember { mutableStateOf<IComponent?>(null) }

        LaunchedEffect(Unit) {
            val bible = BibleFactory.getBible("../asv.zip")
            val reference = Reference("GEN.2")
            val book = bible[reference] ?: error("Book not found")
            val chapter = book[reference]
            component = chapter?.get(reference)
        }

        Column(
            Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = component?.toAnnotatedString(density, style.fontSize) ?: buildAnnotatedString { append("Loading...") },
                inlineContent = emptyMap()
            )
        }
    }
}
