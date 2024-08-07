package com.example.appointment.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appointment.Models.DoctorListModel
import com.example.appointment.R

class DoctorListAdapter(val context: Context, val arrContact:ArrayList<DoctorListModel>) : RecyclerView.Adapter<DoctorListAdapter.ViewHolder>() {

    lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position : Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class ViewHolder(itemView: View , listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val drImage = itemView.findViewById<ImageView>(R.id.drImage)
        val drName = itemView.findViewById<TextView>(R.id.drName)
        val drProfession = itemView.findViewById<TextView>(R.id.drProfession)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val item = LayoutInflater.from(parent.context).inflate(R.layout.doctorlist_content, parent, false)


        return ViewHolder(item , mListener)

    }

    override fun getItemCount(): Int {
        return arrContact.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.drImage.setImageResource(arrContact[position].img)
        holder.drName.text = arrContact[position].name
        holder.drProfession.text = arrContact[position].profession

    }
}