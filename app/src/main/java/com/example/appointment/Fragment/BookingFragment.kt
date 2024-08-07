package com.example.appointment.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appointment.Adapter.BookingListAdapter
import com.example.appointment.FirebaseStoreDataModel.AppointmentModel
import com.example.appointment.Models.BookingListModel
import com.example.appointment.R
import com.example.appointment.databinding.FragmentBookingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class BookingFragment : Fragment(R.layout.fragment_booking) {

    lateinit var database : DatabaseReference
    lateinit var auth : FirebaseAuth
    lateinit var arrBooking : ArrayList<AppointmentModel>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBookingBinding.bind(view)

        auth = FirebaseAuth.getInstance()
        val userUID = auth.currentUser?.uid

        database = FirebaseDatabase.getInstance().getReference("User")
        arrBooking = arrayListOf()

        binding.progressBarBookingFrag.visibility = View.VISIBLE

        database.child(userUID!!).child("Appointment").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrBooking.clear()
                if (snapshot.exists()){
                    for (i in snapshot.children){
                        val Bookings = i.getValue(AppointmentModel::class.java)
                        arrBooking.add(Bookings!!)
                    }
                    binding.progressBarBookingFrag.visibility = View.GONE
                }
                val recyclerAdapter = BookingListAdapter(arrBooking)
                binding.bookingRecycler.adapter = recyclerAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "$error" , Toast.LENGTH_SHORT).show()
            }

        })
        binding.progressBarBookingFrag.visibility = View.GONE

//        arrBooking.add(BookingListModel(R.drawable.b , "Dr.prashant sharma" , "04/01/2024" , "2:00"))
//        arrBooking.add(BookingListModel(R.drawable.profile_image , "Dr.vishal shah" , "04/01/2024" , "2:30"))
//        arrBooking.add(BookingListModel(R.drawable.avatar, "Dr. jayram patel" , "04/01/2024" , "3:00"))

//        binding.bookingRecycler.layoutManager = LinearLayoutManager(context)



    }

}