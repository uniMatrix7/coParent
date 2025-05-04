package com.bignerdranch.android.coparent

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.coparent.data.AppDatabase
import com.bignerdranch.android.coparent.data.EventEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EventViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = AppDatabase.getInstance(app).eventDao()
    @RequiresApi(Build.VERSION_CODES.O)
    private val todayIso = LocalDate.now().format(DateTimeFormatter.ISO_DATE)

    // Flow of upcoming events
    @RequiresApi(Build.VERSION_CODES.O)
    val events = dao.getUpcomingEvents(todayIso)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addEvent(
        name: String,
        dateIso: String,
        time: String,
        participants: String
    ) = viewModelScope.launch {
        dao.insert(EventEntity(
            name = name,
            dateIso = dateIso,
            time = time,
            participants = participants
        ))
    }
}