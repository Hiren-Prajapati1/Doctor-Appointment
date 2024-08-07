package com.example.appointment.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.example.appointment.Activity.Category
import com.example.appointment.Activity.DoctorDetail
import com.example.appointment.Activity.DoctorList
import com.example.appointment.FirebaseStoreDataModel.UserModel
import com.example.appointment.R
import com.example.appointment.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso


class HomeFragment : Fragment(R.layout.fragment_home) {

    var database : DatabaseReference? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)

        val userUID = FirebaseAuth.getInstance().currentUser?.uid

        database = FirebaseDatabase.getInstance().getReference("User")

        if (userUID != null){

            database!!.child(userUID).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val UserData = snapshot.getValue(UserModel::class.java)

                    // Display the name using a TextView
                    binding.ProfileName.setText(UserData!!.name)

                    Picasso.get().load(UserData.profileImage).into(binding.profileImage)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "data retrive error", Toast.LENGTH_SHORT).show()
                }

            })

        }

        binding.topDoctorsSeeAll.setOnClickListener {
            startActivity(Intent(getActivity() , DoctorList::class.java))
        }

        binding.doctorSpecialistSeeAll.setOnClickListener {
            startActivity(Intent(context, Category::class.java))
        }


//        binding.firstAppoinment.setOnClickListener {
//            val image = binding.firstAppointmentImage.drawable.hashCode()
//            val name = binding.firstAppointmentName.text.toString()
//            val profession = binding.firstAppointmentProfession.text.toString()
//
//            val intent = Intent(activity, DoctorDetail::class.java)
//            intent.putExtra("image2" , image)
//            intent.putExtra("name" , name)
//            intent.putExtra("profession" , profession)
//            startActivity(intent)
//
//        }
    }

}