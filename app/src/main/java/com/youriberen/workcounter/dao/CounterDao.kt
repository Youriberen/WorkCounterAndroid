package com.youriberen.workcounter.dao

import androidx.room.*
import com.youriberen.workcounter.model.Counter

@Dao
interface CounterDao {
    @Query("SELECT * FROM historyTable")
    suspend fun getAll(): List<Counter>

    @Insert
    suspend fun insert(counter: Counter)

    @Delete
    suspend fun delete(counter: Counter)

    @Update
    suspend fun update(counter: Counter)
}