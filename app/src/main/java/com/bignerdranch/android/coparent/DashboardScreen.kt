package com.bignerdranch.android.coparent

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.coparent.data.AgreementEntity
import com.bignerdranch.android.coparent.data.EventEntity
import com.bignerdranch.android.coparent.data.TransactionEntity
import com.bignerdranch.android.coparent.ui.theme.BlackBackground
import com.bignerdranch.android.coparent.ui.theme.CoParentTheme
import com.bignerdranch.android.coparent.ui.theme.WhiteText
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashboardScreen(
    familyName:     String,
    numChildren:    Int,
    scheduleEvents: List<EventEntity>,
    transactions: List<TransactionEntity>,
    agreements:     List<AgreementEntity>,

    onMenuClick:      () -> Unit,
    onSettingsClick:  () -> Unit,

    onFinancesEdit:   () -> Unit,
    onWhatIf:         () -> Unit,
    onApprovals:      () -> Unit,

    onScheduleAdd:    () -> Unit,
    onScheduleEdit:   () -> Unit,

    onAgreementsAdd:  () -> Unit,
    onAgreementsEdit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackBackground)
            .padding(16.dp)
    ) {
        // ── Top app bar ────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = WhiteText)
            }
            Text(
                "CoParent",
                style = MaterialTheme.typography.titleLarge.copy(color = WhiteText),
                textAlign = TextAlign.Center
            )
            IconButton(onClick = onSettingsClick) {
                Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = WhiteText)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ── User info / Dashboard label ────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                familyName,
                style = MaterialTheme.typography.bodyLarge.copy(color = WhiteText)
            )
            Text(
                "Dashboard",
                style = MaterialTheme.typography.titleMedium.copy(color = WhiteText)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Face, contentDescription = "Children", tint = WhiteText)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    numChildren.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(color = WhiteText)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ── Finances box ───────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Gray)
                .padding(8.dp)
        ) {
            Column {
                // Header: title + Edit & Add icons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Finances",
                        style = MaterialTheme.typography.titleMedium.copy(color = WhiteText)
                    )
                    Row {
                        IconButton(onClick = onFinancesEdit) {
                            Icon(Icons.Filled.Edit, contentDescription = "Edit Finances", tint = WhiteText)
                        }
                        IconButton(onClick = onWhatIf) {
                            Icon(Icons.Filled.Info, contentDescription = "What If", tint = WhiteText)
                        }
                        IconButton(onClick = onApprovals) {
                            Icon(Icons.Filled.Check, contentDescription = "Approvals", tint = WhiteText)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Summary: Mom / Total / Dad
                val totalMom = transactions.filter { it.person == "Mom" }.sumOf { it.amount }
                val totalDad = transactions.filter { it.person == "Dad" }.sumOf { it.amount }
                val totalAll = totalMom + totalDad

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Mom: \$${"%.2f".format(totalMom)}", color = WhiteText)
                    Text("Total: \$${"%.2f".format(totalAll)}", color = WhiteText)
                    Text("Dad: \$${"%.2f".format(totalDad)}", color = WhiteText)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Transaction list
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    transactions.forEach { tx ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .background(Color(0xFF800080))  // purple
                                .padding(8.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(tx.payee, color = WhiteText)                           // top-left: payee
                                    Text("\$${"%.2f".format(tx.amount)}", color = WhiteText)     // top-right: amount
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    tx.person,
                                    color = WhiteText,
                                    modifier = Modifier.align(Alignment.End)                     // bottom-right: person
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ── Bottom row: Schedule & Agreements ──────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            // Schedule box (left)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.Gray)
                    .padding(8.dp)
            ) {
                Column {
                    // Header row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Schedule",
                            style = MaterialTheme.typography.titleMedium.copy(color = WhiteText)
                        )
                        Row {
                            IconButton(onClick = onScheduleAdd) {
                                Icon(Icons.Filled.Add, contentDescription = "Add Schedule", tint = WhiteText)
                            }
                            IconButton(onClick = onScheduleEdit) {
                                Icon(Icons.Filled.Edit, contentDescription = "Edit Schedule", tint = WhiteText)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Event list
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                    ) {
                        scheduleEvents.forEach { event ->
                            val date = LocalDate.parse(event.dateIso)
                                .format(DateTimeFormatter.ofPattern("MM/dd/yy"))
                            val time = event.time

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .background(Color(0xFF800080))
                                    .padding(8.dp)
                            ) {
                                Column {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(date, color = WhiteText)
                                        Text(time, color = WhiteText)
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(event.name, color = WhiteText)
                                        Text(event.participants, color = WhiteText)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Agreements box (right)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.Gray)
                    .padding(8.dp)
            ) {
                Column {
                    // Header row
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Agreements",
                            style = MaterialTheme.typography.titleMedium.copy(color = WhiteText)
                        )
                        Row {
                            IconButton(onClick = onAgreementsAdd) {
                                Icon(Icons.Filled.Add, contentDescription = "Add Agreement", tint = WhiteText)
                            }
                            IconButton(onClick = onAgreementsEdit) {
                                Icon(Icons.Filled.Edit, contentDescription = "Edit Agreement", tint = WhiteText)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Display nicknames alphabetically
                    Column(
                        Modifier.verticalScroll(rememberScrollState())
                    ) {
                        agreements
                            .sortedBy { it.nickname }
                            .forEach { ag ->
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .background(Color(0xFF800080))
                                        .padding(8.dp)
                                ) {
                                    Text(ag.nickname, color = WhiteText)
                                }
                            }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    name = "Dashboard Screen with Transactions",
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewDashboardScreen() {
    CoParentTheme {
        DashboardScreen(
            familyName      = "Smith Family",
            numChildren     = 3,

            // sample upcoming events
            scheduleEvents = listOf(
                EventEntity(name = "Doctor Appointment", dateIso = "2025-05-02", time = "09:00", participants = "Mom"),
                EventEntity(name = "Soccer Practice",   dateIso = "2025-05-05", time = "18:30", participants = "Dad,Observer"),
                EventEntity(name = "Family Lunch",      dateIso = "2025-05-10", time = "12:00", participants = "Mom,Dad")
            ),

            // sample finances transactions
            transactions = listOf(
                TransactionEntity(id = 1, userEmail = "smith@example.com", dateIso = "2025-05-01", payee = "Grocery",       category = "Food",      amount = 120.50, person = "Mom"),
                TransactionEntity(id = 2, userEmail = "smith@example.com", dateIso = "2025-05-03", payee = "Electric Bill", category = "Utilities", amount =  80.00, person = "Dad"),
                TransactionEntity(id = 3, userEmail = "smith@example.com", dateIso = "2025-05-04", payee = "Gas Station",   category = "Fuel",      amount =  45.25, person = "Mom")
            ),

            // sample agreements
            agreements = listOf(
                AgreementEntity(id = 1, nickname = "Lease Agreement",     uri = ""),
                AgreementEntity(id = 2, nickname = "Medical Consent Form", uri = "")
            ),

            onMenuClick     = {},
            onSettingsClick = {},

            onFinancesEdit  = {},
            onWhatIf        = {},
            onApprovals     = {},

            onScheduleAdd   = {},
            onScheduleEdit  = {},

            onAgreementsAdd = {},
            onAgreementsEdit= {}
        )
    }
}
