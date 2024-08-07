package com.example.appointment.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appointment.Adapter.DoctorListAdapter
import com.example.appointment.HomePage
import com.example.appointment.LoginActivity.SignInPage
import com.example.appointment.Models.DoctorListModel
import com.example.appointment.R
import com.example.appointment.databinding.ActivityDoctorListBinding
import com.google.firebase.auth.FirebaseAuth

class DoctorList : AppCompatActivity() {

    lateinit var binding : ActivityDoctorListBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolBar))

        val arrContact = ArrayList<DoctorListModel>()

        arrContact.add(DoctorListModel(R.drawable.b , "Dr prashant sharma" , "Orthopedic"))
        arrContact.add(DoctorListModel(R.drawable.profile_image , "Dr vishal shah" , "Dentist"))
        arrContact.add(DoctorListModel(R.drawable.avatar, "Dr jayram patel" , "Homopethic"))

        binding.recycler.layoutManager = LinearLayoutManager(this)

        var recyclerAdapter = DoctorListAdapter(this , arrContact)
        binding.recycler.adapter = recyclerAdapter

       recyclerAdapter.setOnItemClickListener(object : DoctorListAdapter.onItemClickListener{
           override fun onItemClick(position: Int) {

               val intent = Intent(this@DoctorList , DoctorDetail::class.java)
               intent.putExtra("image" , arrContact[position].img)
               intent.putExtra("name" , arrContact[position].name)
               intent.putExtra("profession" , arrContact[position].profession)
               startActivity(intent)

           }

       })


//        binding.backDoctorListImage.setOnClickListener {
//            finish()
//        }

    }

    // for toolbar both 2 method
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        auth = FirebaseAuth.getInstance()

        when(item.itemId){

            R.id.editName ->{
                Toast.makeText(this , "click edit name", Toast.LENGTH_SHORT).show()

                finish()
//                val i = Intent(this , HomePage::class.java)
//                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(i)
            }

//            R.id.changePhoto ->
//                Toast.makeText(this, "clicked change photo", Toast.LENGTH_SHORT).show()
//
//            R.id.setting ->
//                Toast.makeText(this, "Clicked Setting", Toast.LENGTH_SHORT).show()
//
//            R.id.logout -> {
//
//                auth.signOut()
//
//                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
//
//                val i = Intent(this , SignInPage::class.java)
//                // set the new task and clear flags and this is work to clear all activity from stack and create new task
//                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(i)
//
//            }
        }

        return super.onOptionsItemSelected(item)
    }
}