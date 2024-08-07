package com.example.appointment.Adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appointment.FirebaseStoreDataModel.AppointmentModel
import com.example.appointment.LoginActivity.SignInPage
import com.example.appointment.Models.BookingListModel
import com.example.appointment.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog

class BookingListAdapter(private val arrContent: ArrayList<AppointmentModel>) : RecyclerView.Adapter<BookingListAdapter.ViewHolder>() {

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val BookingImage = itemView.findViewById<ImageView>(R.id.bookingImage)
        val BookingName = itemView.findViewById<TextView>(R.id.bookingName)
        val BookingDate = itemView.findViewById<TextView>(R.id.dateBookingList)
        val BookingTime = itemView.findViewById<TextView>(R.id.timeBookingList)
        val BookingCancle = itemView.findViewById<Button>(R.id.bookingCancle)
        val BookingReschedual = itemView.findViewById<Button>(R.id.bookingReschedual)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.booking_list_content , parent , false))
    }

    override fun getItemCount(): Int {
        return arrContent.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = arrContent[position]
        val userUID = FirebaseAuth.getInstance().currentUser?.uid

        Picasso.get().load(currentItem.image).into(holder.BookingImage)
        holder.BookingName.text = currentItem.name
        holder.BookingDate.text = currentItem.dateSloat
        holder.BookingTime.text = currentItem.timeSloat

        holder.BookingCancle.setOnClickListener {

            val BottomDialog = BottomSheetMaterialDialog.Builder(holder.itemView.context as Activity)
                .setTitle("Cancle")
                .setMessage("Are you sure want to Cancle Appointment")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialogInterface, which ->

                    val database = FirebaseDatabase.getInstance().getReference("User")
                    database.child(userUID!!).child("Appointment").child(currentItem.id.toString()).removeValue()
                        .addOnCompleteListener {
                            Toast.makeText(holder.itemView.context, "Appointment Cancled", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(holder.itemView.context, it.localizedMessage , Toast.LENGTH_SHORT).show()
                        }
                    dialogInterface.dismiss()
                }
                .setNegativeButton("No") { dialogInterface, which ->
                    dialogInterface.dismiss()
                }.build()

            BottomDialog.show()
        }

        holder.BookingReschedual.setOnClickListener {

        }

    }
}