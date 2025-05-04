package com.bignerdranch.android.coparent.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val email: String,
    val password: String,
    val familyName: String,
    val role: String,
    val numChildren: Int
)