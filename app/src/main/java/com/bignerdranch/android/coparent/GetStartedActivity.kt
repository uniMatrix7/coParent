package com.bignerdranch.android.coparent

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.bignerdranch.android.coparent.data.AppDatabase
import com.bignerdranch.android.coparent.data.User
import com.bignerdranch.android.coparent.ui.theme.CoParentTheme
import kotlinx.coroutines.launch

class GetStartedActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoParentTheme {
                GetStartedScreen(
                    onBack = { finish() },
                    onRegister = { email, password, familyName, role, numChildren ->
                        // Insert into Room on a background thread
                        lifecycleScope.launch {
                            try {
                                val db = AppDatabase.getInstance(this@GetStartedActivity)
                                val userDao = db.userDao()
                                userDao.insert(
                                    User(
                                        email      = email,
                                        password   = password,
                                        familyName = familyName,
                                        role       = role,
                                        numChildren = numChildren
                                    )
                                )

                                // Confirm and navigate
                                Toast.makeText(
                                    this@GetStartedActivity,
                                    "Registered!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                startActivity(
                                    Intent(this@GetStartedActivity, LoginActivity::class.java)
                                )
                                finish()
                            } catch (e: Exception) {
                                Toast.makeText(
                                    this@GetStartedActivity,
                                    "Registration failed: ${e.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                )
            }
        }
    }
}