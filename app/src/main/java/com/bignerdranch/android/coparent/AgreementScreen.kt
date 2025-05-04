package com.bignerdranch.android.coparent

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.coparent.data.AgreementEntity
import com.bignerdranch.android.coparent.ui.theme.BlackBackground
import com.bignerdranch.android.coparent.ui.theme.BlueButton
import com.bignerdranch.android.coparent.ui.theme.CoParentTheme
import com.bignerdranch.android.coparent.ui.theme.WhiteText

@Composable
fun AgreementScreen(
    agreements: List<AgreementEntity>,
    onAddAgreement: (nickname: String, uri: String) -> Unit
) {
    // capture LocalContext once
    val context = LocalContext.current

    var showNicknameDialog by remember { mutableStateOf(false) }
    var tempUri by remember { mutableStateOf<Uri?>(null) }

    // File picker launcher
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            tempUri = it
            showNicknameDialog = true
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackBackground)
            .padding(16.dp)
    )  {
        // Top row: title + add-file icon
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(24.dp))
            Text(
                "Agreements",
                style = MaterialTheme.typography.titleLarge.copy(color = WhiteText)
            )
            IconButton(onClick = { launcher.launch(arrayOf("*/*")) }) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add file",
                    tint = WhiteText
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // Background box
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(8.dp)
        ) {
            Column(
                Modifier
                    //.fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Purple cards of nicknames
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

        // Nickname dialog
        if (showNicknameDialog && tempUri != null) {
            NicknameDialog(
                onDismiss = {
                    showNicknameDialog = false
                    tempUri = null
                },
                onSave = { nickname ->
                    onAddAgreement(nickname, tempUri.toString())
                    showNicknameDialog = false
                    tempUri = null
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NicknameDialog(
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var nickname by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Name this file", color = WhiteText) },
        text = {
            OutlinedTextField(
                value = nickname,
                onValueChange = { nickname = it },
                label = { Text("Nickname") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                )
            )
        },
        confirmButton = {
            TextButton(onClick = { if (nickname.isNotBlank()) onSave(nickname) }) {
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

@Preview(
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewAgreementScreen() {
    val sample = listOf(
        AgreementEntity(1, "Lease", "uri1"),
        AgreementEntity(2, "Medical Consent", "uri2")
    )
    CoParentTheme {
        AgreementScreen(sample, onAddAgreement = { _, _ -> })
    }
}
