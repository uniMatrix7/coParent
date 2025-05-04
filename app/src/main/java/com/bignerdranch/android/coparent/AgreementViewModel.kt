package com.bignerdranch.android.coparent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.coparent.data.AppDatabase
import com.bignerdranch.android.coparent.data.AgreementEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AgreementViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = AppDatabase.getInstance(app).agreementDao()

    // Flow of all agreements, alphabetical
    val agreements = dao.getAllAgreements()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addAgreement(nickname: String, uri: String) = viewModelScope.launch {
        dao.insert(AgreementEntity(nickname = nickname, uri = uri))
    }
}
