package com.bignerdranch.android.coparent


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.bignerdranch.android.coparent.data.AppDatabase
import com.bignerdranch.android.coparent.ui.theme.CoParentTheme
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoParentTheme {
                LoginScreen(
                    onBack = { finish() },
                    onForgotPassword = {
                        startActivity(Intent(this, GetStartedActivity::class.java))
                    },
                    onRegister = {
                        startActivity(Intent(this, GetStartedActivity::class.java))
                    },
                    onLogin = { emailInput, passwordInput ->
                        // Launch a coroutine to do the DB lookup off the main thread
                        lifecycleScope.launch {
                            val db = AppDatabase.getInstance(this@LoginActivity)
                            val user = db.userDao().findByEmail(emailInput)

                            if (user != null && user.password == passwordInput) {
                                // Successful login → go to Dashboard
                                startActivity(
                                    Intent(this@LoginActivity, DashboardActivity::class.java)
                                        .putExtra("email", user.email)
                                )
                                finish()
                            } else {
                                // Failed login
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Invalid email or password",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                )
            }
        }
    }
}