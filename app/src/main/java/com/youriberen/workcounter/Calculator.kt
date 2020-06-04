package com.youriberen.workcounter

import android.content.Context
import android.content.SharedPreferences
import java.text.NumberFormat
import java.util.*


class Calculator() {

    //Top
    var hourCounter     = 0.0F
    var moneyCounter    = 0.0F

    //Middle
    var currentMoney    = 0.0F
    var currentHour     = 0.0F

    //Bottom
    var totalMoney      = 0.0F

    var stepperValue    = 0.5F
    var hourlyWage      = 25.0F

    private val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())


    //Used for the - Button
    public fun minStepper() {
        currentHour -= stepperValue
        currentMoney = currentHour * hourlyWage
    }

    public fun plusStepper() {
        currentHour += stepperValue
        currentMoney = currentHour * hourlyWage
    }

    //Add button, add the current hours/money to the hour/money counter
    fun addButton() {
        hourCounter += currentHour
        moneyCounter += currentMoney
        currentMoney = 0.0F
        currentHour = 0.0F
    }

    //Reset function for the counter, resets all counters, earned money wil added to total money
    fun resetCounter() {
        totalMoney += moneyCounter
        hourCounter = 0.0F
        moneyCounter = 0.0F
    }

    fun resetTotal() {
        totalMoney = 0.0F
    }

}