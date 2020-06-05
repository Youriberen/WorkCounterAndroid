package com.youriberen.workcounter.UI

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.youriberen.workcounter.Calculator
import com.youriberen.workcounter.R
import com.youriberen.workcounter.model.Counter
import com.youriberen.workcounter.repository.HistoryRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {

    private val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    private val calculator = Calculator()
    private lateinit var historyRepository: HistoryRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        historyRepository = HistoryRepository(this)
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

        //Save wage
        val wageString = editWage.text.toString()
        editor.putFloat("hourlyWage", wageString.toFloat()).apply()
    }

    fun saveDB() {
        val date = Date()
        val formatter = SimpleDateFormat("dd MMM HH:mm:ss")
        val hour = Counter(formatter.format(date), calculator.currentHour, format.format(calculator.currentMoney))
        println(hour)
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                if (historyRepository != null) {
                    historyRepository.insert(hour)
                }
            }
        }
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
            saveDB()
            calculator.addButton()
            updateLabels()
            saveAllValues()
            getAllValues()
        }

        resetCurrentBtn.setOnClickListener {
            resetAlert()
        }

        historyBtn.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

        editWage.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                calculator.hourlyWage = editWage.text.toString().toFloat()
                saveAllValues()
                return@OnKeyListener true
            }
            false
        })
    }

    private fun updateLabels() {
        currentHoursTV.text = calculator.currentHour.toString()
        currentMoneyTV.text = format.format(calculator.currentMoney)

        totalHoursTV.text = calculator.hourCounter.toString()
        totalMoneyTV.text = format.format(calculator.moneyCounter)

        editWage.setText(calculator.hourlyWage.toString())
    }

    private fun resetAlert() {
            val dialogBuilder = AlertDialog.Builder(this)
            // set message of alert dialog
            dialogBuilder.setMessage("Are you sure you want to reset your hours and salary?")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                        dialog, id ->
                    calculator.resetCounter()
                    updateLabels()
                    saveAllValues()
                })
                // negative button text and action
                .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })

            val alert = dialogBuilder.create()
            alert.setTitle("Reset Counter")
            alert.show()
        }
}
