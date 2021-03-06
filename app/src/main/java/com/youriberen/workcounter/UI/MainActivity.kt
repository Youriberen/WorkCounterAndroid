package com.youriberen.workcounter.UI

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.youriberen.workcounter.Calculator
import com.youriberen.workcounter.MainActivityViewModel
import com.youriberen.workcounter.R
import com.youriberen.workcounter.model.Counter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    private val calculator = Calculator()
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAllValues()
        updateLabels()
        buttons()
    }

    private fun getAllValues() {
        val sharedPreferences = getSharedPreferences("Counter", Context.MODE_PRIVATE)
        calculator.hourCounter  =   sharedPreferences.getFloat("hourCounter", 0.0F)
        calculator.moneyCounter =   sharedPreferences.getFloat("moneyCounter", 0.0F)
        calculator.hourlyWage   =   sharedPreferences.getFloat("hourlyWage", 0.0F)
    }

    //Save to shared preferences
    private fun saveAllValues() {
        //Key = "Counter", all values are store in "Counter"
        val sharedPreferences = getSharedPreferences("Counter", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        //Save value in a key called "HourCounter" in a key called 'Counter"
        editor.putFloat("hourCounter", calculator.hourCounter).apply()
        editor.putFloat("moneyCounter", calculator.moneyCounter).apply()

        //Save wage, editText = string convert to float
        val wageString = editWage.text.toString()
        editor.putFloat("hourlyWage", wageString.toFloat()).apply()
    }

    //Save to database
    private fun saveDB() {
        val date = Date()
        val formatter = SimpleDateFormat("dd MMM HH:mm:ss")
        val hour = Counter(formatter.format(date), calculator.currentHour, format.format(calculator.currentMoney))
        println(hour)
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                if (viewModel != null) {
                    viewModel.insert(hour)
                }
            }
        }
    }

    //All buttons
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
            resetAlert()    //Reset alert is called
        }

        //Use of intent
        historyBtn.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            // start new activity
            startActivity(intent)
        }

        //When enter is pressed editWage is saved, isempty than error message
        editWage.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //Check if string is empty
                val wage = editWage.text.toString()
                if(wage.isEmpty() || wage == "."){
                    editWage.error = "Please fill in a number"
                    return@OnKeyListener false
                } else {
                    calculator.hourlyWage = wage.toFloat()
                }

                saveAllValues()
                return@OnKeyListener true
            }
            false
        })
    }

    //Every time a button is pressed the labels are updated
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
