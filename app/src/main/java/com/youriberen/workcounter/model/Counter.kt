package com.youriberen.workcounter.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "historyTable",
    indices = [Index("date")])
data class Counter(
    @PrimaryKey
    @ColumnInfo(name = "date")
    var workedDate: String,

    @ColumnInfo(name = "hours")
    var workedHours: Float,

    @ColumnInfo(name = "money")
    var earnedMoney: String
)