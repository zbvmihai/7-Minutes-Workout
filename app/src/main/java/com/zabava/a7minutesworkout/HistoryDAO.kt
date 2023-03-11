package com.zabava.a7minutesworkout

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDAO {
    @Insert
    suspend fun insert(HistoryEntity: HistoryEntity)

    @Update
    suspend fun update(HistoryEntity: HistoryEntity)

    @Query("SELECT * FROM 'history-table'")
    fun fetchAllDates(): Flow<List<HistoryEntity>>
}