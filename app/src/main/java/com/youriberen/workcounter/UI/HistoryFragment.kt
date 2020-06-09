package com.youriberen.workcounter.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.youriberen.workcounter.MainActivityViewModel
import com.youriberen.workcounter.R
import com.youriberen.workcounter.model.Counter
import com.youriberen.workcounter.repository.HistoryRepository
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryFragment : Fragment() {

    private var history: ArrayList<Counter> = arrayListOf()

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        initRv()
    }

    private fun observeViewModel() {
        viewModel.counters.observe(viewLifecycleOwner, Observer { counters ->
            this@HistoryFragment.history.clear()
            this@HistoryFragment.history.addAll(counters)
            historyAdapter.notifyDataSetChanged()
        })
    }


    private fun initRv() {

        historyAdapter = HistoryAdapter(history)
        viewManager = LinearLayoutManager(activity)

        createItemTouchHelper().attachToRecyclerView(rvHistory)

        rvHistory.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = historyAdapter
        }
    }

    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                val historyToDelete = history[position]
                viewModel.delete(historyToDelete)

            }
        }
        return ItemTouchHelper(callback)
    }


}
