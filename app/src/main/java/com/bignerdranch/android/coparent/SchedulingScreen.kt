package com.bignerdranch.android.coparent

import android.os.Build
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.bignerdranch.android.coparent.data.EventEntity
import com.bignerdranch.android.coparent.ui.theme.BlackBackground
import com.bignerdranch.android.coparent.ui.theme.BlueButton
import com.bignerdranch.android.coparent.ui.theme.CoParentTheme
import com.bignerdranch.android.coparent.ui.theme.WhiteText
import java.time.LocalDate
import java.time.format.DateTimeFormatter

enum class ViewMode { Month, Week, Year }

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SchedulingScreen(
    events: List<EventEntity>,
    onSaveEvent: (name: String, dateIso: String, time: String, participants: String) -> Unit
) {
    var viewMode   by remember { mutableStateOf(ViewMode.Month) }
    var showDialog by remember { mutableStateOf(false) }
    var pickedDate by remember { mutableStateOf(LocalDate.now()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackBackground)
            .padding(16.dp)
    ) {
        Text(
            text = "Scheduling",
            style = MaterialTheme.typography.titleLarge.copy(
                color = WhiteText,
                fontSize = 28.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(16.dp))

        // Mode toggle
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            ViewMode.values().forEach { mode ->
                Button(
                    onClick = { viewMode = mode },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (viewMode == mode) BlueButton else Color.DarkGray
                    )
                ) {
                    Text(mode.name, color = WhiteText)
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Calendar
        when (viewMode) {
            ViewMode.Month -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(Color.Gray)
                ) {
                    AndroidView(
                        factory = { ctx ->
                            CalendarView(ctx).apply {
                                setOnDateChangeListener { _, year, month, day ->
                                    pickedDate = LocalDate.of(year, month + 1, day)
                                    showDialog = true
                                }
                            }
                        },
                        modifier = Modifier.matchParentSize()
                    )
                }
            }
            ViewMode.Week -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Week view coming soon", color = WhiteText)
                }
            }
            ViewMode.Year -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Year view coming soon", color = WhiteText)
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Event list
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            events.forEach { e ->
                val displayDate = LocalDate
                    .parse(e.dateIso)
                    .format(DateTimeFormatter.ofPattern("MM/dd/yy"))
                val displayTime = e.time

                Box(
                    modifier = Modifier
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
                            Text(displayDate, color = WhiteText)
                            Text(displayTime, color = WhiteText)
                        }
                        Spacer(Modifier.height(4.dp))
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(e.name, color = WhiteText)
                            Text(e.participants, color = WhiteText)
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddEventDialog(
            initialDate = pickedDate,
            onDismiss   = { showDialog = false },
            onSave      = { name, date, time, participants ->
                onSaveEvent(name, date, time, participants)
                showDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEventDialog(
    initialDate: LocalDate,
    onDismiss:   () -> Unit,
    onSave:      (name: String, dateIso: String, time: String, participants: String) -> Unit
) {
    var name         by remember { mutableStateOf("") }
    var dateIso      by remember {
        mutableStateOf(initialDate.format(DateTimeFormatter.ISO_DATE))
    }
    var time         by remember { mutableStateOf("") }
    val participants = remember { mutableStateMapOf(
        "Mom" to false,
        "Dad" to false,
        "Request" to false,
        "Other" to false
    ) }
    var notes        by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Event", color = WhiteText) },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Event Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White
                    )
                )
                Spacer(Modifier.height(8.dp))
                Text("Date: ${LocalDate.parse(dateIso).format(DateTimeFormatter.ofPattern("MM/dd/yy"))}", color = WhiteText)
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Time (HH:mm)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White
                    )
                )
                Spacer(Modifier.height(8.dp))
                Text("Who is attending?", color = WhiteText)
                participants.forEach { (role, selected) ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = selected,
                            onCheckedChange = { participants[role] = it },
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = WhiteText,
                                checkedColor   = BlueButton
                            )
                        )
                        Text(role, color = WhiteText)
                    }
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White
                    )
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val whoCsv = participants
                    .filter { it.value }
                    .keys
                    .joinToString(",")
                onSave(name, dateIso, time, whoCsv)
            }) {
                Text("Save", color = BlueButton)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = WhiteText)
            }
        },
        containerColor = Color.DarkGray
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewSchedulingScreen() {
    val sampleEvents = listOf(
        EventEntity(1, "Doctor", "2025-05-02", "09:00", "Mom"),
        EventEntity(2, "Soccer", "2025-05-05", "18:30", "Dad,Observer")
    )

    CoParentTheme {
        SchedulingScreen(
            events = sampleEvents,
            onSaveEvent = { _, _, _, _ -> }
        )
    }
}