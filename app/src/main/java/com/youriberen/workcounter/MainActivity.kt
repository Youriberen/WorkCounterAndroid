package com.youriberen.workcounter

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    var totalEarned     = 0.0

    private val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())

    private val calculator = Calculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        getAllValues()
        updateLabels()
        buttons()
    }

    fun getAllValues() {
        val sharedPreferences = getSharedPreferences("Counter", Context.MODE_PRIVATE)
        calculator.hourCounter  =   sharedPreferences.getFloat("hourCounter", 0.0F)
        calculator.moneyCounter =   sharedPreferences.getFloat("moneyCounter", 0.0F)
        calculator.hourlyWage   =   sharedPreferences.getFloat("hourlyWage", 25.0F)
        calculator.totalMoney   =   sharedPreferences.getFloat("totalMoney", 0.0F)
    }

    fun saveAllValues() {
        val sharedPreferences = getSharedPreferences("Counter", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("hourCounter", calculator.hourCounter).apply()
        editor.putFloat("moneyCounter", calculator.moneyCounter).apply()
        editor.putFloat("totalMoney", calculator.totalMoney).apply()
        editor.putString("test", "Hallo").apply()
    }

    private fun buttons() {
        addBtn.setOnClickListener {
            calculator.plusStepper()
            updateLabels()
        }
        minBtn.setOnClickListener {
            calculator.minStepper()
            updateLabels()
        }

        addCurrentBtn.setOnClickListener {
            calculator.addButton()
            updateLabels()
            saveAllValues()
            getAllValues()
        }

        resetCurrentBtn.setOnClickListener {
            calculator.resetCounter()
            updateLabels()
            saveAllValues()
        }

        resetTotalBtn.setOnClickListener {
            calculator.resetTotal()
            updateLabels()
            saveAllValues()
        }
    }

    private fun updateLabels() {
        currentHoursTV.text = calculator.currentHour.toString()
        currentMoneyTV.text = format.format(calculator.currentMoney)

        totalHoursTV.text = calculator.hourCounter.toString()
        totalMoneyTV.text = format.format(calculator.moneyCounter)

        totalEarnedTV.text = format.format(calculator.totalMoney)
    }
}
