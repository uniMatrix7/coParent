package com.bignerdranch.android.coparent.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.bignerdranch.android.coparent.AgreementScreen
import com.bignerdranch.android.coparent.AgreementViewModel
import com.bignerdranch.android.coparent.ui.theme.CoParentTheme

class AgreementsActivity : ComponentActivity() {
    private val viewModel: AgreementViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoParentTheme {
                val agreements = viewModel.agreements.collectAsState().value
                AgreementScreen(
                    agreements = agreements,
                    onAddAgreement = { nickname, uri ->
                        viewModel.addAgreement(nickname, uri)
                    }
                )
            }
        }
    }
}
