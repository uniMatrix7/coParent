package com.bignerdranch.android.coparent.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bignerdranch.android.coparent.ApprovalsScreen
import com.bignerdranch.android.coparent.ui.theme.CoParentTheme

class ApprovalsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoParentTheme {
                ApprovalsScreen()
            }
        }
    }
}