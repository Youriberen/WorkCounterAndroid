package com.youriberen.workcounter.repository

import android.content.Context
import com.youriberen.workcounter.CounterDatabase
import com.youriberen.workcounter.dao.CounterDao
import com.youriberen.workcounter.model.Counter


public class HistoryRepository(context: Context) {

    private var counterDao: CounterDao

    init {
        val counterDatabase =
            CounterDatabase.getDatabase(context)
        counterDao = counterDatabase!!.counterDao()
    }


    suspend fun getAll(): List<Counter> {
        return counterDao.getAll()
    }

    suspend fun insert(counter: Counter) {
        counterDao.insert(counter)
    }

    suspend fun delete(counter: Counter) {
        counterDao.delete(counter)
    }

    suspend fun update(counter: Counter) {
        counterDao.update(counter)
    }

}