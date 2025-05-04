package com.bignerdranch.android.coparent.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AgreementDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(agreement: AgreementEntity)

    @Query("SELECT * FROM agreements ORDER BY nickname")
    fun getAllAgreements(): Flow<List<AgreementEntity>>
}
