package com.example.appointment.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.appointment.FirebaseStoreDataModel.UserModel
import com.example.appointment.R
import com.example.appointment.databinding.ActivityHomePageBinding
import com.example.appointment.databinding.ActivityUpdateProfileBinding
import com.example.appointment.databinding.ActivityUploadProfileImageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import com.shashank.sony.fancytoastlib.FancyToast
import java.util.regex.Pattern

class UpdateProfile : AppCompatActivity() {

    lateinit var binding: ActivityUpdateProfileBinding
    var database : DatabaseReference? = null
    lateinit var storage : StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userUID = FirebaseAuth.getInstance().currentUser?.uid

        database = FirebaseDatabase.getInstance().getReference("User")

        if (userUID != null){

            database!!.child(userUID).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val UserData = snapshot.getValue(UserModel::class.java)

                    // Display the name using a TextView
                    binding.editTextUpdateProfileName.setText(UserData!!.name)
                    binding.editTextUpdateProfileNumber.setText(UserData.number)

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        }

        binding.buttonUpdateProfile.setOnClickListener {

            val Updatename = binding.editTextUpdateProfileName.text.toString()
            val Updatenumber = binding.editTextUpdateProfileNumber.text.toString()

            val userdata = UserModel(Updatename , Updatenumber)
            val map = mapOf<String , Any>(
                "name" to Updatename,
                "number" to Updatenumber
            )

            val mobileRegex = "[6-9][0-9]{9}"
            val mobilePattern = Pattern.compile(mobileRegex)
            val mobileMatcher = mobilePattern.matcher(binding.editTextUpdateProfileNumber.text.toString())


            if (!mobileMatcher.find()) {
                binding.editTextUpdateProfileNumber.setError("Invalid Phone Number")

            }else if (binding.editTextUpdateProfileName != null){
                binding.editTextUpdateProfileNumber.setError("Name Reqired")
            }else{
                database!!.child(userUID!!).updateChildren(map).addOnCompleteListener {

                    FancyToast.makeText(this , "Profile Updated" , FancyToast.LENGTH_SHORT , FancyToast.SUCCESS , false).show()
                    finish()

                }.addOnFailureListener {
                    Toast.makeText(this, "Data not Update", Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.backUpdateProfile.setOnClickListener {
            finish()
        }
    }
}