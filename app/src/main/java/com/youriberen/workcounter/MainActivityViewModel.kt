package com.youriberen.workcounter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.youriberen.workcounter.model.Counter
import com.youriberen.workcounter.repository.HistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val historyRepository = HistoryRepository(application.applicationContext)

    val counters: LiveData<List<Counter>> = historyRepository.getAll()

    fun insert(counter: Counter) {
        ioScope.launch {
            historyRepository.insert(counter)
        }
    }

    fun delete(counter: Counter) {
        ioScope.launch {
            historyRepository.delete(counter)
        }
    }

}
