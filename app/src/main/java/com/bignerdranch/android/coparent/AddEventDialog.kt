package com.bignerdranch.android.coparent

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.bignerdranch.android.coparent.ui.theme.WhiteText
import com.bignerdranch.android.coparent.ui.theme.BlueButton

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEventDialog(
    initialDate: LocalDate,
    onDismiss:   () -> Unit,
    onSave: (
        name: String,
        dateIso: String,
        time: String,
        location: String,
        participants: Set<String>,
        repeat: String,
        notes: String
    ) -> Unit
) {
    // field state
    var name         by remember { mutableStateOf("") }
    var dateIso      by remember {
        mutableStateOf(initialDate.format(DateTimeFormatter.ISO_DATE))
    }
    var time         by remember { mutableStateOf("") }
    var location     by remember { mutableStateOf("") }
    val participants = remember {
        mutableStateMapOf(
            "Mom"     to false,
            "Dad"     to false,
            "Request" to false,
            "Other"   to false
        )
    }
    var repeat       by remember { mutableStateOf("") }
    var notes        by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Add Event", color = WhiteText)
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Event Name
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

                // Date (read-only)
                Text(
                    "Date: ${LocalDate.parse(dateIso).format(DateTimeFormatter.ofPattern("MM/dd/yy"))}",
                    color = WhiteText
                )
                Spacer(Modifier.height(8.dp))

                // Time
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

                // Location
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Where") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White
                    )
                )
                Spacer(Modifier.height(8.dp))

                // Participants
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

                // Repeat
                OutlinedTextField(
                    value = repeat,
                    onValueChange = { repeat = it },
                    label = { Text("Repeat Options") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White
                    )
                )
                Spacer(Modifier.height(8.dp))

                // Notes
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White
                    )
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                // collect selected participants
                val whoSet = participants.filter { it.value }.keys.toSet()
                onSave(name, dateIso, time, location, whoSet, repeat, notes)
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
