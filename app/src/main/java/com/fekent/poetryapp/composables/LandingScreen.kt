package com.fekent.poetryapp.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
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
import com.fekent.poetryapp.data.authoredExample
import com.fekent.poetryapp.ui.theme.PoetryAppTheme
import com.fekent.poetryapp.ui.theme.abeezeeFont
import com.fekent.poetryapp.ui.theme.aboretoFont

@Composable
fun LandingScreen() {
    LandingScreenUI()
}

@Composable
private fun LandingScreenUI() {
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
            authoredExample.forEach { item ->
                AuthoredCard(poem = item)
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}


@Composable
fun AuthoredCard(poem: Authored) {
    Card() {
        Surface {
            Column {
                Text(
                    poem.title.toString(),
                    fontFamily = aboretoFont,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1
                )
                Text(
                    poem.poem,
                    fontFamily = abeezeeFont,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    maxLines = 1
                )
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun LandingScreenPreview() {
    PoetryAppTheme {
        LandingScreenUI()
    }
}
