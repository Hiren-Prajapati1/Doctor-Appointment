package com.example.appointment.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appointment.Models.CategoryModel
import com.example.appointment.R

class CategoryAdapter(val context: Context, val arrContent: ArrayList<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val categoryimg = itemView.findViewById<ImageView>(R.id.categoryImage)
        val categoryname = itemView.findViewById<TextView>(R.id.categoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_content , parent , false))
    }

    override fun getItemCount(): Int {
        return arrContent.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.categoryimg.setImageResource(arrContent[position].categoryImage)
        holder.categoryname.text = arrContent[position].categoryName

    }
}