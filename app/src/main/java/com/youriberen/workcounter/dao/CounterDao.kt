package com.youriberen.workcounter.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.youriberen.workcounter.model.Counter

@Dao
interface CounterDao {
    @Query("SELECT * FROM historyTable")
    fun getAll(): LiveData<List<Counter>>

    @Insert
    fun insert(counter: Counter)

    @Delete
    suspend fun delete(counter: Counter)

    @Update
    suspend fun update(counter: Counter)
}