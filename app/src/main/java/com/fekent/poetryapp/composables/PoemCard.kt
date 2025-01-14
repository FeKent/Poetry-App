package com.fekent.poetryapp.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fekent.poetryapp.data.Authored
import com.fekent.poetryapp.data.Saved
import com.fekent.poetryapp.ui.theme.PoetryAppTheme
import com.fekent.poetryapp.ui.theme.abeezeeFont
import com.fekent.poetryapp.ui.theme.aboretoFont

@Composable
fun PoemCard(
    authored: Authored?,
    saved: Saved?,
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Spacer(Modifier.size(16.dp))
            if (authored != null || saved != null) {
                (authored?.title ?: saved?.title)?.let {
                    Text(
                        text = it,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontFamily = aboretoFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    )
                }
            }
            Spacer(Modifier.size(16.dp))
            Box(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (authored != null || saved != null) {
                    (authored?.poem ?: saved?.poem)?.let {
                        Text(text = it, fontFamily = abeezeeFont, fontWeight = FontWeight.Light)
                    }
                }
            }
            Spacer(Modifier.size(16.dp))
            if (saved != null) {
                Text(
                    text = saved.author,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontFamily = aboretoFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Spacer(Modifier.size(16.dp))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PoemScreenPreview() {
    PoetryAppTheme {
        PoemCard(
            authored = null, saved = Saved(
                0, "Fire and Ice", "Some say the world will end in fire,\n" +
                        "Some say in ice.\n" +
                        "From what I’ve tasted of desire\n" +
                        "I hold with those who favor fire.\n" +
                        "But if it had to perish twice,\n" +
                        "I think I know enough of hate\n" +
                        "To say that for destruction ice\n" +
                        "Is also great\n" +
                        "And would suffice.", "Robert Frost"
            )
        )
    }
}