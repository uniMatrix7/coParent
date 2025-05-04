package com.bignerdranch.android.coparent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
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
import com.bignerdranch.android.coparent.ui.theme.BlackBackground
import com.bignerdranch.android.coparent.ui.theme.BlueButton
import com.bignerdranch.android.coparent.ui.theme.CoParentTheme
import com.bignerdranch.android.coparent.ui.theme.WhiteText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onBack:           () -> Unit,
    onLogin:          (email: String, password: String) -> Unit,
    onForgotPassword: () -> Unit,
    onRegister:       () -> Unit
) {
    // form state
    var email    by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackBackground)
    ) {
        // 1) Back arrow at top left
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = WhiteText
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(56.dp))

            // 2) Top photo
            Image(
                painter = painterResource(id = R.drawable.profile_photo),
                contentDescription = "Login Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(150.dp))

            // 3) Email field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White,
                    //textColor      = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 4) Password field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White,
                    //textColor      = Color.Black
                )
            )

            // 5) Forgot Password link
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Forgot Password?",
                    style = MaterialTheme.typography.bodyLarge.copy(color = BlueButton),
                    modifier = Modifier.clickable(onClick = onForgotPassword)
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            // 6) Login button
            Button(
                onClick    = { onLogin(email, password) },
                colors     = ButtonDefaults.buttonColors(containerColor = BlueButton),
                modifier   = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Login", color = WhiteText)
            }
        }

        // 7) Register link at bottom right
        Text(
            text = "Register",
            style = MaterialTheme.typography.bodyLarge.copy(color = BlueButton),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .clickable(onClick = onRegister)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewLoginScreen() {
    CoParentTheme {
        LoginScreen(
            onBack            = {},
            onLogin           = {email, password -> },
            onForgotPassword  = {},
            onRegister        = {}
        )
    }
}
