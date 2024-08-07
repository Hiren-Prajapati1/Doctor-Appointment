package com.example.appointment.Activity

import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.appointment.Adapter.DateDaySelectAppointmentAdapter
import com.example.appointment.FirebaseStoreDataModel.AppointmentModel
import com.example.appointment.Models.DateDaySelectAppointmentModel
import com.example.appointment.R
import com.example.appointment.databinding.ActivitySelectAppointmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import java.util.Locale
import java.util.UUID

class SelectAppointment : AppCompatActivity() {

    lateinit var binding: ActivitySelectAppointmentBinding
    private var selectedButton: Button? = null
    private var database : DatabaseReference? = null
    private lateinit var auth : FirebaseAuth
    private lateinit var storage : StorageReference
    private var uri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val userUID = auth.currentUser?.uid

        val doctorImage = intent.extras!!.getInt("Image")
        val docctorName = intent.extras!!.getString("Name")
        val docctorProfession = intent.extras!!.getString("Profession")

        // set the data from recyclerview of doctor list
        binding.selectAppointmentImage.setImageResource(doctorImage)
        binding.selectAppointmentName.text = docctorName
        binding.selectAppointmentProfession.text = docctorProfession

        // this session used for select day
        val daysList = mutableListOf<DateDaySelectAppointmentModel>()
        for (i in 0 until 7) {
            val date = getDateFromCurrentDate(i)
            val day = getDayFromDate(date)
            daysList.add(DateDaySelectAppointmentModel(date, day))
        }

        // set adapter
        val daysAdapter = DateDaySelectAppointmentAdapter(daysList)
        binding.daysRecyclerView.adapter = daysAdapter



        // this session is used for select time sloat
        val TimeSloat = arrayOf(
            binding.frame200,
            binding.frame230,
            binding.frame300,
            binding.frame330,
            binding.frame400
        )

        TimeSloat.forEach { time ->
            time.setOnClickListener {
                selectTime(it as Button)
            }
        }

        binding.btnMakeAppointment.setOnClickListener {

            val AppointmentID = UUID.randomUUID().toString()

            val doctorName = binding.selectAppointmentName.text.toString()
            val doctorProfession = binding.selectAppointmentProfession.text.toString()
            val timeSloat = selectedButton?.text.toString()
            val dateSloat = daysAdapter.getSelectedDate()

            if (selectedButton == null && daysAdapter.getSelectedDate() == null){
                Toast.makeText(this, "Please, Select Day and Time Sloat", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (selectedButton == null){
                Toast.makeText(this, "Please, Select time sloat", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (daysAdapter.getSelectedDate() == null){
                Toast.makeText(this, "Please, Select Day", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val BottomSheetDialog = BottomSheetMaterialDialog.Builder(this)
                .setTitle("Book Appointment")
                .setMessage("Are you sure want to Book Appointment")
                .setCancelable(false)
                .setPositiveButton("Book", R.drawable.ic_booking) { dialogInter, which ->

                    val dialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                    dialog.progressHelper.barColor = Color.parseColor("#A5DCB6")
                    dialog.setTitleText("loading")
                    dialog.setCancelable(true)
                    dialog.show()

                    database = FirebaseDatabase.getInstance().getReference("User")
                    storage = FirebaseStorage.getInstance().getReference("Doctor_Images")

                    val imageUri = Uri.parse("android.resource://$packageName/${doctorImage}")


                    if (userUID != null){
                        storage.child(userUID).child(AppointmentID).putFile(imageUri)
                            .addOnSuccessListener { task ->
                                task.metadata!!.reference!!.downloadUrl
                                    .addOnSuccessListener {url ->

                                        val imageUrl = url.toString()

                                        val AppintmentDetail = AppointmentModel(AppointmentID , imageUrl ,doctorName , doctorProfession , timeSloat , dateSloat)

                                        database!!.child(userUID).child("Appointment").child(AppointmentID).setValue(AppintmentDetail).addOnCompleteListener {

                                            dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                                            dialog.setTitleText("Success")
                                            dialog.setContentText("Appointment Booked")
                                            dialog.setConfirmButton("Done") {
                                                dialog.dismissWithAnimation()
                                                finish()
                                            }.show()

                                            binding.btnMakeAppointment.isEnabled = false
                                            binding.btnMakeAppointment.setBackgroundColor(Color.GRAY)

                                        }.addOnFailureListener {
                                            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                                        }
                                    }
                            }
                    }else{
                        Toast.makeText(this, "uid error", Toast.LENGTH_SHORT).show()
                    }

                    dialogInter.dismiss()

                }
                .setNegativeButton("Cancel") { dialogInter, which ->
                    dialogInter.dismiss()
                }.build()
            // Show Dialog
            BottomSheetDialog.show()

        }

        binding.backSelectAppointment.setOnClickListener {
            finish()
        }

    }



    private fun selectTime(button: Button) {
        selectedButton?.isSelected = false
        selectedButton?.setBackgroundResource(R.drawable.frame_book_time_sloat) // Change background color to nonselect for deselected button

        button.isSelected = true
        button.setBackgroundResource(R.drawable.color_book_time_sloat) // Change background color to select for selected button
        selectedButton = button
    }

    fun getDayFromDate(date: String): String {
        val dateFormat = android.icu.text.SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(date)
        val dayFormat = android.icu.text.SimpleDateFormat("EEEE", Locale.getDefault())
        return dayFormat.format(calendar.time)
    }

    fun getDateFromCurrentDate(day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, day)
        val dateFormat = android.icu.text.SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}