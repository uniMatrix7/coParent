package com.bignerdranch.android.coparent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.bignerdranch.android.coparent.ui.theme.BlackBackground
import com.bignerdranch.android.coparent.ui.theme.CoParentTheme
import com.bignerdranch.android.coparent.ui.theme.WhiteText

@Composable
fun WhatIfScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "What If",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = WhiteText,
                    fontSize = 28.sp
                ),
                textAlign = TextAlign.Center
            )
            // …add your content here…
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewWhatIfScreen() {
    CoParentTheme {
        WhatIfScreen()
    }
}