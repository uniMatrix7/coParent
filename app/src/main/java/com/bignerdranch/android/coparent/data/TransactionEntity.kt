package com.bignerdranch.android.coparent.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userEmail: String,
    val dateIso: String,     // "yyyy-MM-dd"
    val payee: String,
    val category: String,
    val amount: Double,
    val person: String       // "Mom" or "Dad"
)
