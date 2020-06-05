package com.youriberen.workcounter

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.youriberen.workcounter.dao.CounterDao
import com.youriberen.workcounter.model.Counter

@Database(entities = [Counter::class], version = 1, exportSchema = false)
abstract class CounterDatabase : RoomDatabase() {

    abstract fun counterDao(): CounterDao

    companion object {
        private const val DATABASE_NAME = "COUNTER_DATABASE"

        @Volatile
        private var counterRoomDatabaseInstance: CounterDatabase? = null

        fun getDatabase(context: Context): CounterDatabase? {
            if (counterRoomDatabaseInstance == null) {
                synchronized(CounterDatabase::class.java) {
                    if (counterRoomDatabaseInstance == null) {
                        counterRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            CounterDatabase::class.java,
                            DATABASE_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return counterRoomDatabaseInstance
        }
    }

}
