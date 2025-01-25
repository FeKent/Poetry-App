package com.fekent.poetryapp.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fekent.poetryapp.data.Authored
import com.fekent.poetryapp.data.Saved
import com.fekent.poetryapp.ui.theme.PoetryAppTheme

@Composable
fun AnnotationScreen() {
    AnnotationScreenUI(authored = null, saved = null)
}

@Composable
fun AnnotationScreenUI(authored: Authored?, saved: Saved?) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.size(32.dp))
        Text(text = "Annotate: ${authored?.title ?: saved?.title.toString()}")
        Spacer(Modifier.size(32.dp))
        SelectionContainer {
            Spacer(Modifier.size(8.dp))
            Text(text = authored?.poem ?: saved?.poem.toString(), lineHeight = 32.sp)
            Spacer(Modifier.size(8.dp))
        }


    }

}

@Preview (showSystemUi = true)
@Composable
private fun AnnotationScreenPreview() {
    PoetryAppTheme {
        AnnotationScreenUI(
            authored = Authored(1, "Fire and Ice", "Some say the world will end in fire,\n" +
                    "Some say in ice.\n" +
                    "From what I’ve tasted of desire\n" +
                    "I hold with those who favor fire.\n" +
                    "But if it had to perish twice,\n" +
                    "I think I know enough of hate\n" +
                    "To say that for destruction ice\n" +
                    "Is also great\n" +
                    "And would suffice."),
            saved = null
        )
    }
}