package com.bignerdranch.android.coparent.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import com.bignerdranch.android.coparent.TransactionViewModel
import com.bignerdranch.android.coparent.ui.theme.CoParentTheme
import com.bignerdranch.android.coparent.FinancesScreen

class FinancesActivity : ComponentActivity() {
    private val txViewModel: TransactionViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoParentTheme {
                val tx = txViewModel.transactions.collectAsState().value
                FinancesScreen(
                    transactions     = tx,
                    onAddTransaction = { dateIso, payee, category, amount, person ->
                        txViewModel.addTransaction(dateIso, payee, category, amount, person)
                    },
                    onEdit           = { /* future editing */ }
                )
            }
        }
    }
}
