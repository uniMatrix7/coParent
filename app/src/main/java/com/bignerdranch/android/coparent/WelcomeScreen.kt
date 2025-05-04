package com.bignerdranch.android.coparent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.coparent.ui.theme.BlackBackground
import com.bignerdranch.android.coparent.ui.theme.BlueButton
import com.bignerdranch.android.coparent.ui.theme.CoParentTheme
import com.bignerdranch.android.coparent.ui.theme.WhiteText
import java.lang.reflect.Modifier

class WelcomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoParentTheme {
            }
        }
    }
}

@Composable
fun WelcomeScreen(
    onGetStarted: () -> Unit,
    onLogin:      () -> Unit
) {
    Box(
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
            .background(BlackBackground)
            .padding(16.dp)
    ) {
        Column(
            modifier = androidx.compose.ui.Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement  = Arrangement.SpaceBetween
        ) {
            // ── Top half: Image ─────────────────────────────
            Image(
                painter           = painterResource(R.drawable.profile_photo),
                contentDescription = "Welcome photo",
                contentScale      = ContentScale.Crop,
                modifier          = androidx.compose.ui.Modifier
                    .fillMaxWidth()
                    .height(450.dp),
                    //.weight(1f)
            )

            Spacer(modifier = androidx.compose.ui.Modifier.height(45.dp))

            // ── Title ───────────────────────────────────────
            Text(
                text  = "CoParent",
                style = MaterialTheme.typography.titleLarge.copy(color = WhiteText),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))

            // ── Subtitle ────────────────────────────────────
            Text(
                text  = "Track and Split Expenses Effortlessly",
                style = MaterialTheme.typography.titleMedium.copy(color = WhiteText),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = androidx.compose.ui.Modifier.height(100.dp))

            // ── Buttons ─────────────────────────────────────
            Column(
                modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick    = onGetStarted,
                    colors     = ButtonDefaults.buttonColors(containerColor = BlueButton),
                    modifier   = androidx.compose.ui.Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text("Get Started", color = WhiteText)
                }
                Button(
                    onClick  = onLogin,
                    colors   = ButtonDefaults.buttonColors(containerColor = BlueButton),
                    modifier = androidx.compose.ui.Modifier
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
}
