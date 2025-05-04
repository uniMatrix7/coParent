package com.bignerdranch.android.coparent.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(tx: TransactionEntity)

    @Query("""
    SELECT * FROM transactions
    WHERE userEmail = :email
    ORDER BY dateIso DESC, amount DESC
  """)
    fun getByUser(email: String): Flow<List<TransactionEntity>>
}
