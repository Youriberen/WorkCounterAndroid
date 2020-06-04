package com.youriberen.workcounter

import java.text.NumberFormat
import java.util.*

class Calculator {

    var hourCounter = 0.0
    var moneyCounter = 0.0

    var currentMoney = 0.0
    var currentHour = 0.0

    var totalMoney = 0.0
    var stepperValue = 0.5

    var hourlyWage = 10.0

    private val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())

    fun getAllValues() {
        //All values from the room
    }

    public fun minStepper(): String {
        currentHour -= stepperValue
        currentMoney = currentHour * hourlyWage
        return format.format(currentMoney)
    }

    public fun plusStepper(): String {
        currentHour += stepperValue
        currentMoney = currentHour * hourlyWage
        return format.format(currentMoney)
    }

    fun addButton() {
        hourCounter += currentHour
        moneyCounter += currentMoney
    }
}