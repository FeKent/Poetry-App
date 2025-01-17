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
import androidx.compose.foundation.layout.wrapContentWidth
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
import com.fekent.poetryapp.data.Saved
import com.fekent.poetryapp.data.savedExamples
import com.fekent.poetryapp.ui.theme.PoetryAppTheme
import com.fekent.poetryapp.ui.theme.abeezeeFont
import com.fekent.poetryapp.ui.theme.aboretoFont

@Composable
fun SavedScreen(
    savedPoems: List<Saved>,
    editPoem: (Saved) -> Unit,
    deletePoem: (Saved) -> Unit
) {
    var selectedPoem by remember { mutableStateOf<Saved?>(null) }
    var isPopupVisible by remember { mutableStateOf(false) }
    SavedScreenUI(
        onPoemTap = { poem ->
            selectedPoem = poem
            isPopupVisible = true
        },
        onPopupDismiss = {
            isPopupVisible = false
            selectedPoem = null
        },
        onUpdatePopupVisibility = { isVisible ->
            isPopupVisible = isVisible
        },
        selectedPoem = selectedPoem,
        isPopupVisible = isPopupVisible,
        savedPoems = savedPoems,
        editPoem = editPoem,
        deletePoem = deletePoem
    )
}

@Composable
private fun SavedScreenUI(
    onPoemTap: (Saved) -> Unit,
    onPopupDismiss: () -> Unit,
    onUpdatePopupVisibility: (Boolean) -> Unit,
    selectedPoem: Saved?,
    isPopupVisible: Boolean,
    savedPoems: List<Saved>,
    editPoem: (Saved) -> Unit,
    deletePoem: (Saved) -> Unit
) {
    Column(Modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(32.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = "This is where you can save your favourite lines from other poems",
                fontFamily = abeezeeFont,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.size(32.dp))
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            savedPoems.forEach { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    SavedCard(poem = item, onPoemTap = onPoemTap)
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
                authored = null,
                saved = selectedPoem,
                editPoem = { _, saved ->
                    if (saved != null) {
                        editPoem(saved)
                    }
                },
                deletePoem = { _, saved ->
                    if (saved != null) {
                        deletePoem(saved)
                        onUpdatePopupVisibility(false)
                    }
                },
            )
        }
    }

}

@Composable
fun SavedCard(poem: Saved, onPoemTap: (Saved) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clickable { onPoemTap(poem) },
        colors = CardDefaults.cardColors(contentColor = MaterialTheme.colorScheme.primary)
    ) {
        Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    poem.title.toString(),
                    fontFamily = aboretoFont,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textDecoration = TextDecoration.Underline,
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "by ",
                    fontFamily = abeezeeFont,
                    fontWeight = FontWeight.Thin,
                    fontSize = 14.sp
                )
                Text(
                    text = poem.author,
                    fontFamily = aboretoFont,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                )
            }
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                poem.poem.lines().first().toString().removeTrailingPunctuation(),
                fontFamily = abeezeeFont,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun SavedScreenPreview() {
    PoetryAppTheme {
        SavedScreenUI(
            isPopupVisible = false,
            onPoemTap = {},
            onPopupDismiss = {},
            onUpdatePopupVisibility = {},
            selectedPoem = null,
            savedPoems = savedExamples,
            editPoem = {},
            deletePoem = {},
        )
    }
}