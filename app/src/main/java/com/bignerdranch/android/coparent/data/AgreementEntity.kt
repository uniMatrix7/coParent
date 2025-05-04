package com.bignerdranch.android.coparent.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agreements")
data class AgreementEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nickname: String,
    val uri: String
)
