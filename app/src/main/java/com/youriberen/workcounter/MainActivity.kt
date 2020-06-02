package com.youriberen.workcounter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    var currentHour     = 0.0
    var currentMoney    = 0.0
    var totalHour       = 0.0
    var totalMoney      = 0.0
    var totalEarned     = 0.0
    var hourlyWage      = 25
    val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLabels()
        calculate()
        addButton()
        resetCurrent()
    }

    private fun initLabels() {
        currentHoursTV.text = "0"
        currentMoneyTV.text = "€ 0"
        currentHour = 0.0
        currentMoney = 0.0
    }

    private fun calculate() {
        addBtn.setOnClickListener {
            currentHour += 0.5
            currentMoney = currentHour * hourlyWage

            currentHoursTV.text = currentHour.toString()
            currentMoneyTV.text = format.format(currentMoney)
        }
        minBtn.setOnClickListener {
            currentHour -= 0.5
            currentMoney = currentHour * hourlyWage

            currentHoursTV.text = currentHour.toString()
            currentMoneyTV.text = format.format(currentMoney)
        }

    }

    private fun addButton() {
        addCurrentBtn.setOnClickListener {
            totalHour += currentHour
            totalMoney += currentMoney
            totalHoursTV.text = totalHour.toString()
            totalMoneyTV.text = format.format(totalMoney)
            initLabels()
        }
    }

    private fun resetCurrent() {
        resetCurrentBtn.setOnClickListener {
            totalHour = 0.0
            totalHoursTV.text = "0"
            totalMoneyTV.text = "0"
            totalEarned += totalMoney

            totalEarnedTV.text = format.format(totalEarned)
        }
    }
}
