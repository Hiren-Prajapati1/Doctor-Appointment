package com.example.appointment.Activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.appointment.databinding.ActivityDoctorDetailBinding

class DoctorDetail : AppCompatActivity() {

    lateinit var binding: ActivityDoctorDetailBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get the data from doctorlist
      //  val bundle : Bundle?= intent.extras
        val doctorImage = intent.extras!!.getInt("image")
        val doctorName = intent.extras!!.getString("name")
        val doctorProfession = intent.extras!!.getString("profession")

        // set the data from recyclerview of doctor list
        binding.doctorDetailImage.setImageResource(doctorImage)
        binding.doctorDetailName.text = doctorName
        binding.doctorDetailProfession.text = doctorProfession


        binding.backDoctorDetailImage.setOnClickListener {
            finish()
        }

        binding.btnBookAppointment.setOnClickListener {
            val i = Intent(this , SelectAppointment::class.java)
            i.putExtra("Image" , doctorImage)
            i.putExtra("Name" , doctorName)
            i.putExtra("Profession" , doctorProfession)
            startActivity(i)
        }
    }
}