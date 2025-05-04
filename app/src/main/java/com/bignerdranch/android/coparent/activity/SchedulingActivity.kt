package com.bignerdranch.android.coparent.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import com.bignerdranch.android.coparent.EventViewModel
import com.bignerdranch.android.coparent.SchedulingScreen
import com.bignerdranch.android.coparent.ui.theme.CoParentTheme

class SchedulingActivity : ComponentActivity() {
    private val viewModel: EventViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoParentTheme {
                SchedulingScreen(
                    events        = viewModel.events.collectAsState().value,
                    onSaveEvent   = { name, dateIso, time, participants ->
                        viewModel.addEvent(name, dateIso, time, participants)
                    }
                )
            }
        }
    }
}