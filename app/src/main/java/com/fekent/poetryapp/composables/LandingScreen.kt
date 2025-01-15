package com.fekent.poetryapp.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.fekent.poetryapp.data.Authored
import com.fekent.poetryapp.data.authoredExample
import com.fekent.poetryapp.ui.theme.PoetryAppTheme
import com.fekent.poetryapp.ui.theme.abeezeeFont
import com.fekent.poetryapp.ui.theme.aboretoFont

@Composable
fun LandingScreen(authoredPoems: List<Authored>) {

    var selectedPoem by remember { mutableStateOf<Authored?>(null) }
    var isPopupVisible by remember { mutableStateOf(false) }
    LandingScreenUI(
        onPoemTap = { poem ->
            selectedPoem = poem
            isPopupVisible = true
        },
        onPopupDismiss = {
            isPopupVisible = false
            selectedPoem = null
        },
        selectedPoem = selectedPoem,
        isPopupVisible = isPopupVisible,
        authoredPoems = authoredPoems
    )
}

@Composable
private fun LandingScreenUI(
    onPoemTap: (Authored) -> Unit,
    onPopupDismiss: () -> Unit,
    selectedPoem: Authored?,
    isPopupVisible: Boolean,
    authoredPoems: List<Authored>
) {
    Column(Modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(32.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Welcome to the Poetry App, write your own poems, and save your favourite lines",
                fontFamily = abeezeeFont,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.size(32.dp))
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            authoredPoems.forEach { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AuthoredCard(poem = item, onPoemTap = onPoemTap)
                }
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }

    if (isPopupVisible && selectedPoem != null) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = { onPopupDismiss() }
        ) {
            PoemCard(
                authored = selectedPoem,
                saved = null
            )
        }
    }
}


@Composable
fun AuthoredCard(poem: Authored, onPoemTap: (Authored) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clickable {
                onPoemTap(poem)
            },
        colors = CardDefaults.cardColors(contentColor = MaterialTheme.colorScheme.primary)
    ) {
        Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
            Text(
                poem.title.toString(),
                fontFamily = aboretoFont,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                poem.poem.lines().first().toString().removeTrailingPunctuation(),
                fontFamily = abeezeeFont,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}

fun String.removeTrailingPunctuation(): String {
    val regex = Regex("[\\p{Punct}]+$")
    return this.replace(regex, "")
}


@Preview(showSystemUi = true)
@Composable
private fun LandingScreenPreview() {
    PoetryAppTheme {
        LandingScreenUI(
            isPopupVisible = false,
            onPoemTap = {},
            onPopupDismiss = {},
            selectedPoem = null,
            authoredPoems = authoredExample
        )
    }
}
