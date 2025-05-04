package com.bignerdranch.android.coparent

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.bignerdranch.android.coparent.activity.AgreementsActivity
import com.bignerdranch.android.coparent.activity.FinancesActivity
import com.bignerdranch.android.coparent.activity.SchedulingActivity
import com.bignerdranch.android.coparent.data.AppDatabase
import com.bignerdranch.android.coparent.data.User
import com.bignerdranch.android.coparent.ui.theme.CoParentTheme

class DashboardActivity : ComponentActivity() {

    private val eventViewModel: EventViewModel by viewModels()
    private val agreementViewModel: AgreementViewModel by viewModels()
    private val transactionViewModel: TransactionViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the logged-in user’s email from the Intent
        val userEmail = intent.getStringExtra("email") ?: ""

        setContent {
            CoParentTheme {
                // Load the User once
                var user by remember { mutableStateOf<User?>(null) }
                LaunchedEffect(userEmail) {
                    user = AppDatabase
                        .getInstance(this@DashboardActivity)
                        .userDao()
                        .findByEmail(userEmail)
                }

                // Observe events, agreements, and transactions
                val events        by eventViewModel.events.collectAsState()
                val agreements    by agreementViewModel.agreements.collectAsState()
                val transactions  by transactionViewModel.transactions.collectAsState()

                // Render once the user is loaded
                user?.let { u ->
                    DashboardScreen(
                        familyName      = u.familyName,
                        numChildren     = u.numChildren,
                        scheduleEvents  = events,
                        transactions    = transactions,
                        agreements      = agreements,

                        onMenuClick     = { /* TODO: open nav drawer */ },
                        onSettingsClick = { /* TODO: open settings */ },

                        onFinancesEdit  = {
                            startActivity(
                                Intent(this, FinancesActivity::class.java)
                            )
                        },
                        onWhatIf        = {
                            startActivity(
                                Intent(this, FinancesActivity::class.java)
                            )
                        },
                        onApprovals     = {
                            startActivity(
                                Intent(this, FinancesActivity::class.java)
                            )
                        },

                        onScheduleAdd   = {
                            startActivity(
                                Intent(this, SchedulingActivity::class.java)
                            )
                        },
                        onScheduleEdit  = {
                            startActivity(
                                Intent(this, SchedulingActivity::class.java)
                            )
                        },

                        onAgreementsAdd = {
                            startActivity(
                                Intent(this, AgreementsActivity::class.java)
                            )
                        },
                        onAgreementsEdit= {
                            startActivity(
                                Intent(this, AgreementsActivity::class.java)
                            )
                        }
                    )
                }
            }
        }
    }
}
