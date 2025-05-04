package com.bignerdranch.android.coparent

import android.text.Selection.setSelection
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import android.widget.Spinner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bignerdranch.android.coparent.ui.theme.BlackBackground
import com.bignerdranch.android.coparent.ui.theme.BlueButton
import com.bignerdranch.android.coparent.ui.theme.CoParentTheme
import com.bignerdranch.android.coparent.ui.theme.WhiteText
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetStartedScreen(
    onBack:           () -> Unit,
    onRegister: (email: String,
                 password: String,
                 familyName: String,
                 role: String,
                 numChildren: Int) -> Unit
) {
    // State for text fields
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var familyName by remember { mutableStateOf("") }
    var numChildren by remember { mutableIntStateOf(0) }
    var selectedRole by remember { mutableStateOf("Mom") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BlackBackground)
        )
        {
            // ← Back arrow at top-left
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    //.align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = WhiteText
                )
            }

            // ── Top half: Image ─────────────────────────────
            Image(
                painter = painterResource(R.drawable.profile_photo),
                contentDescription = "Welcome photo",
                modifier = Modifier
                    .fillMaxWidth()
                    //.weight(1f),
                    .height(330.dp),
                contentScale = ContentScale.Crop
            )

            // ── Bottom half: Gray form container ────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.Gray)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    // Title
                    Text(
                        text = "Parent Registration",
                        style = MaterialTheme.typography.titleLarge.copy(color = WhiteText),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    // Email field
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    // Password field
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    // Family Name field
                    OutlinedTextField(
                        value = familyName,
                        onValueChange = { familyName = it },
                        label = { Text("Create Family Code Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Select Role and Number of Children",
                        style = MaterialTheme.typography.bodyLarge.copy(color = WhiteText),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 1) Spinner for Mom/Dad/Observer
                        AndroidView(
                            factory = { context ->
                                Spinner(context).apply {
                                    adapter = ArrayAdapter(
                                        context,
                                        android.R.layout.simple_spinner_dropdown_item,
                                        listOf("Mom", "Dad", "Observer")
                                    )
                                    setSelection(0)
                                    onItemSelectedListener =
                                        object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(
                                                parent: AdapterView<*>?,
                                                view: android.view.View?,
                                                pos: Int,
                                                id: Long
                                            ) {
                                                selectedRole =
                                                    parent?.getItemAtPosition(pos) as String
                                            }

                                            override fun onNothingSelected(parent: AdapterView<*>?) {}
                                        }
                                    // white background
                                    setBackgroundColor(android.graphics.Color.WHITE)
                                }
                            },
                            modifier = Modifier
                                .width(140.dp)
                                .height(56.dp)
                        )

                        // 2) NumberPicker for number of children
                        AndroidView(
                            factory = { context ->
                                NumberPicker(context).apply {
                                    minValue = 0
                                    maxValue = 10
                                    value = numChildren
                                    setOnValueChangedListener { _, _, new ->
                                        numChildren = new
                                    }
                                    setBackgroundColor(android.graphics.Color.WHITE)
                                }
                            },
                            modifier = Modifier
                                .width(140.dp)
                                .height(56.dp)
                        )
                    }

                    // Register button
                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            onRegister(
                                email,
                                password,
                                familyName,
                                selectedRole,
                                numChildren
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BlueButton),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text("Register", color = WhiteText)
                    }

                    Button(
                        onClick = { /* TODO: handle registration */ },
                        colors = ButtonDefaults.buttonColors(containerColor = BlueButton),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text("Request", color = WhiteText)
                    }
                }
            }
        }
    }

@Preview(
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFF000000  // black bg to match your screen
)
    @Composable
    fun PreviewGetStartedScreen() {
        CoParentTheme {
            GetStartedScreen(
                onBack = { /* noop for preview */ },
                onRegister = { email, password, familyName, role, numChildren ->
                    /* noop for preview */
                }
            )
        }
    }