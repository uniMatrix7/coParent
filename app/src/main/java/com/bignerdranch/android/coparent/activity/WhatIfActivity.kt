package com.bignerdranch.android.coparent.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bignerdranch.android.coparent.WhatIfScreen
import com.bignerdranch.android.coparent.ui.theme.CoParentTheme

class WhatIfActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoParentTheme {
                WhatIfScreen()
            }
        }
    }
}