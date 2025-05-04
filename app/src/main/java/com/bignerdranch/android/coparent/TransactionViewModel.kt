package com.bignerdranch.android.coparent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.coparent.data.AppDatabase
import com.bignerdranch.android.coparent.data.TransactionEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TransactionViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = AppDatabase.getInstance(app).transactionDao()
    private val prefs = app.getSharedPreferences("CoParentPrefs", 0)
    private val email = prefs.getString("email", "").orEmpty()

    // Flow of all transactions for current user
    val transactions = dao.getByUser(email)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addTransaction(
        dateIso: String,
        payee: String,
        category: String,
        amount: Double,
        person: String
    ) = viewModelScope.launch {
        dao.insert(TransactionEntity(
            userEmail = email,
            dateIso   = dateIso,
            payee     = payee,
            category  = category,
            amount    = amount,
            person    = person
        ))
    }
}
