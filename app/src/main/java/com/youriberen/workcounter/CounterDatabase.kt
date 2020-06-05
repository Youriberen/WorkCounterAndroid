package com.youriberen.workcounter

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.youriberen.workcounter.dao.CounterDao
import com.youriberen.workcounter.model.Counter

private const val DATABASE = "historyTable"

@Database(
    entities = [Counter::class],
    version = 1,
    exportSchema = false
)
abstract class CounterDatabase : RoomDatabase() {

    abstract fun counterData(): CounterDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: CounterDatabase? = null

        fun getInstance(context: Context): CounterDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): CounterDatabase {
            return Room.databaseBuilder(context, CounterDatabase::class.java, DATABASE)
                .build()
        }
    }
}