package com.bignerdranch.android.coparent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.coparent.ui.theme.BlackBackground
import com.bignerdranch.android.coparent.ui.theme.CoParentTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.coparent.ui.theme.BlueButton
import com.bignerdranch.android.coparent.ui.theme.WhiteText

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoParentTheme {
                //WelcomeScreen(
                //    onGetStarted = { /* TODO: nav to sign-up */ },
                //   onLogin      = { /* TODO: nav to login  */ }
                //)

            }
        }
    }
}

/*@Composable
fun WelcomeScreen(
    onGetStarted: () -> Unit,
    onLogin:      () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackBackground)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement  = Arrangement.SpaceBetween
        ) {
            // ── Top half: Image ─────────────────────────────
            Image(
                painter           = painterResource(R.drawable.profile_photo),
                contentDescription = "Welcome photo",
                contentScale      = ContentScale.Crop,
                modifier          = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            // ── Title ───────────────────────────────────────
            Text(
                text  = "CoParent",
                style = MaterialTheme.typography.titleLarge.copy(color = WhiteText),
                textAlign = TextAlign.Center
            )

            // ── Subtitle ────────────────────────────────────
            Text(
                text  = "Track and Split Expenses Effortlessly",
                style = MaterialTheme.typography.titleMedium.copy(color = WhiteText),
                textAlign = TextAlign.Center
            )

            // ── Buttons ─────────────────────────────────────
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick    = onGetStarted,
                    colors     = ButtonDefaults.buttonColors(containerColor = BlueButton),
                    modifier   = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text("Get Started", color = WhiteText)
                }
                Button(
                    onClick  = onLogin,
                    colors   = ButtonDefaults.buttonColors(containerColor = BlueButton),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text("Login", color = WhiteText)
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000,    // black background
    showSystemUi = true,
    name = "Welcome Screen Preview"
)
@Composable
fun WelcomeScreenPreview() {
    CoParentTheme {
        WelcomeScreen(
            onGetStarted = {},
            onLogin      = {}
        )
    }
}*/