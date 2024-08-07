package com.example.appointment.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.appointment.Models.DateDaySelectAppointmentModel
import com.example.appointment.R

class DateDaySelectAppointmentAdapter(private val days: List<DateDaySelectAppointmentModel>) : RecyclerView.Adapter<DateDaySelectAppointmentAdapter.ViewHolder>() {

    val selectedItems = mutableListOf<Boolean>().apply {
        for(i in 0..days.size)
            add(false)
    }

    fun getSelectedDate(): String? {
        for (i in selectedItems.indices) {
            if (selectedItems[i]) {
                return days[i].date
            }
        }
        return null
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayText: TextView = itemView.findViewById(R.id.day_text)
        val dateText : TextView = itemView.findViewById(R.id.date_text)
        val llrow : ConstraintLayout= itemView.findViewById(R.id.llrow)

        // this init session is used for selected item which if-else condition declare in onBindViewHolder
        init {
            llrow.setOnClickListener {
                if (days[adapterPosition].day == "Sunday" || days[adapterPosition].day == "sunday") {
                    return@setOnClickListener
                }
                val previousPosition = selectedItems.indexOf(true)
                if (previousPosition != -1) {
                    selectedItems[previousPosition] = false
                    notifyItemChanged(previousPosition)
                }
                selectedItems[adapterPosition] = true
                notifyItemChanged(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.date_day_content_of_selectappoitment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = days[position]
        holder.dayText.text = day.day

        val date = days[position]
        holder.dateText.text = date.date

        // this session for select only one item and change background as selected item
        if (selectedItems[position]) {
            holder.llrow.setBackgroundResource(R.drawable.color_book_time_sloat)
        } else if (days[position].day == "Sunday" || days[position].day == "sunday") {
            holder.llrow.setBackgroundResource(R.drawable.disable_book_time_sloat)
        }else{
            holder.llrow.setBackgroundResource(R.drawable.frame_book_time_sloat)
        }

    }

    override fun getItemCount() = days.size
}