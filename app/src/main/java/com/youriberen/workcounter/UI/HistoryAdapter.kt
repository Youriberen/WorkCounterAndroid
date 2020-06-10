package com.youriberen.workcounter.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youriberen.workcounter.R
import com.youriberen.workcounter.model.Counter
import kotlinx.android.synthetic.main.item_history.view.*

class HistoryAdapter(
    private val counters: ArrayList<Counter>
) : RecyclerView.Adapter<HistoryAdapter.ReminderViewHolder>()  {

    //Labels are filled from the counter array
    inner class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(counter: Counter) {
            itemView.dateTV.text = counter.workedDate
            itemView.hourTV.text = counter.workedHours.toString()
            itemView.moneyTV.text = counter.earnedMoney
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)

        return ReminderViewHolder(view)
    }

    override fun getItemCount(): Int = counters.size

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.bind(counters[position])
    }


}