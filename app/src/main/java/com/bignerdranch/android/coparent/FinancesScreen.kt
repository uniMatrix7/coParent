package com.bignerdranch.android.coparent

import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.coparent.data.TransactionEntity
import com.bignerdranch.android.coparent.ui.theme.BlackBackground
import com.bignerdranch.android.coparent.ui.theme.BlueButton
import com.bignerdranch.android.coparent.ui.theme.WhiteText
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.ui.tooling.preview.Preview
import com.bignerdranch.android.coparent.ui.theme.CoParentTheme


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FinancesScreen(
    transactions: List<TransactionEntity>,
    onAddTransaction: (
        dateIso: String,
        payee: String,
        category: String,
        amount: Double,
        person: String
    ) -> Unit,
    onEdit: (TransactionEntity) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .background(BlackBackground)
            .padding(16.dp)
    ) {
        // Top bar: title + icons
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(24.dp))
            Text("Finances", style = MaterialTheme.typography.titleLarge.copy(color = WhiteText))
            Row {
                IconButton(onClick = { /* future edit-all */ }) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit All", tint = WhiteText)
                }
                IconButton(onClick = { showDialog = true }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Transaction", tint = WhiteText)
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Summary row
        val totalMom  = transactions.filter { it.person=="Mom" }.sumOf { it.amount }
        val totalDad  = transactions.filter { it.person=="Dad" }.sumOf { it.amount }
        val totalAll  = totalMom + totalDad

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Mom: \$${"%.2f".format(totalMom)}", color = WhiteText)
            Text("Total: \$${"%.2f".format(totalAll)}", color = WhiteText)
            Text("Dad: \$${"%.2f".format(totalDad)}", color = WhiteText)
        }

        Spacer(Modifier.height(16.dp))

        // Transaction list
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(8.dp)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                transactions.forEach { tx ->
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(Color(0xFF800080))
                            .padding(8.dp)
                    ) {
                        Column {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(tx.payee, color = WhiteText)
                                Text("\$${"%.2f".format(tx.amount)}", color = WhiteText)
                            }
                            Spacer(Modifier.height(4.dp))
                            Text(tx.person, color = WhiteText, modifier=Modifier.align(Alignment.End))
                        }
                    }
                }
            }
        }

        // Add-transaction dialog
        if (showDialog) {
            AddTransactionDialog(
                onDismiss = { showDialog = false },
                onSave = { dateIso, payee, category, amount, person ->
                    onAddTransaction(dateIso, payee, category, amount, person)
                    showDialog = false
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionDialog(
    onDismiss: () -> Unit,
    onSave: (
        dateIso: String,
        payee: String,
        category: String,
        amount: Double,
        person: String
    ) -> Unit
) {
    // State
    var payee    by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var amount   by remember { mutableStateOf("") }
    var person   by remember { mutableStateOf("Mom") }
    val dateState = rememberDatePickerState(initialSelectedDateMillis = LocalDate.now()
        .format(DateTimeFormatter.ISO_DATE)
        .let { LocalDate.parse(it).toEpochDay() * 24 * 60 * 60 * 1000 })

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Transaction", color = WhiteText) },
        text = {
            Column {
                // Date
                DatePicker(
                    state = dateState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
                Spacer(Modifier.height(8.dp))

                // Payee
                OutlinedTextField(
                    value = payee,
                    onValueChange = { payee = it },
                    label = { Text("Payee") },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(containerColor=Color.White)
                )
                Spacer(Modifier.height(8.dp))

                // Category
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(containerColor=Color.White)
                )
                Spacer(Modifier.height(8.dp))

                // Amount
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    singleLine = true,
                    keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number),
                    colors = TextFieldDefaults.outlinedTextFieldColors(containerColor=Color.White)
                )
                Spacer(Modifier.height(8.dp))

                // Person slider
                Text("Person: $person", color=WhiteText)
                Slider(
                    value = if(person=="Mom") 0f else 1f,
                    onValueChange = { person = if (it<0.5f) "Mom" else "Dad" },
                    steps = 0
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val selectedDate = LocalDate.ofEpochDay(dateState.selectedDateMillis!!/(1000*60*60*24))
                    .format(DateTimeFormatter.ISO_DATE)
                onSave(
                    selectedDate,
                    payee,
                    category,
                    amount.toDoubleOrNull() ?: 0.0,
                    person
                )
            }) {
                Text("Save", color=BlueButton)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color=WhiteText)
            }
        },
        containerColor = Color.DarkGray
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    name = "Finances Screen",
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewFinancesScreen() {
    CoParentTheme {
        FinancesScreen(
            transactions = listOf(
                TransactionEntity(
                    id = 1,
                    userEmail = "user@example.com",
                    dateIso = "2025-05-01",
                    payee = "Grocery Store",
                    category = "Food",
                    amount = 123.45,
                    person = "Mom"
                ),
                TransactionEntity(
                    id = 2,
                    userEmail = "user@example.com",
                    dateIso = "2025-05-02",
                    payee = "Gas Station",
                    category = "Fuel",
                    amount = 50.00,
                    person = "Dad"
                ),
                TransactionEntity(
                    id = 3,
                    userEmail = "user@example.com",
                    dateIso = "2025-05-03",
                    payee = "Electric Bill",
                    category = "Utilities",
                    amount = 75.20,
                    person = "Mom"
                )
            ),
            onAddTransaction = { _, _, _, _, _ -> /* no-op */ },
            onEdit = { /* no-op */ }
        )
    }
}