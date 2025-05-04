package com.bignerdranch.android.coparent.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val dateIso: String,       // store as "yyyy-MM-dd"
    val time: String,          // store as "HH:mm"
    val participants: String   // comma-separated, e.g. "Mom,Dad"
)