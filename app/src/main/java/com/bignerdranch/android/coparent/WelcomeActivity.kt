package com.bignerdranch.android.coparent

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bignerdranch.android.coparent.ui.theme.CoParentTheme

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoParentTheme {
                WelcomeScreen(
                     onGetStarted = { startActivity(Intent(this, GetStartedActivity::class.java))
                          },
                      onLogin = { startActivity(Intent(this, LoginActivity::class.java))
                          }
                )
            }
        }

    }
}
