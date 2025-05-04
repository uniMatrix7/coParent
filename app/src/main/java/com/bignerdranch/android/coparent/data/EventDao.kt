package com.bignerdranch.android.coparent.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(event: EventEntity)

    // get all future events, sorted by date & time
    @Query("""
    SELECT * FROM events
    WHERE dateIso >= :today
    ORDER BY dateIso, time
  """)
    fun getUpcomingEvents(today: String): Flow<List<EventEntity>>
}