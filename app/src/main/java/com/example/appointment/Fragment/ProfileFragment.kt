package com.example.appointment.Fragment

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.appointment.Activity.UpdateProfile
import com.example.appointment.Activity.UploadProfileImage
import com.example.appointment.FirebaseStoreDataModel.UserModel
import com.example.appointment.LoginActivity.SignInPage
import com.example.appointment.R
import com.example.appointment.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import java.io.File


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    lateinit var auth : FirebaseAuth
    var database : DatabaseReference? = null
    lateinit var storage : StorageReference


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentProfileBinding.bind(view)

        auth = FirebaseAuth.getInstance()
        val userUID = auth.currentUser?.uid

        database = FirebaseDatabase.getInstance().getReference("User")
        storage = FirebaseStorage.getInstance().getReference("Images")

        binding.progressBarProfile.visibility = View.VISIBLE

        if (userUID != null){

            database!!.child(userUID).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val UserData = snapshot.getValue(UserModel::class.java)

                    // Display the name using a TextView
                    binding.profileUserName.setText(UserData!!.name)

                    Picasso.get().load(UserData.profileImage).into(binding.profileUserImage)

//                    storage = FirebaseStorage.getInstance().getReference("Profile Images")
//                    val localFile = File.createTempFile("tempImage" , "jpg")
//                    storage.child("$userUID.jpg").getFile(localFile).addOnSuccessListener {
//
//                        val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
//                        binding.profileUserImage.setImageBitmap(bitmap)
//                    }.addOnFailureListener{
//                        Toast.makeText(context, "Profile Image is not change", Toast.LENGTH_SHORT).show()
//                    }

                    binding.progressBarProfile.visibility = View.GONE
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "data retrive error", Toast.LENGTH_SHORT).show()
                }

            })

        }


        binding.profileLogOut.setOnClickListener {

            val mBottomSheetDialog = BottomSheetMaterialDialog.Builder(context as Activity)
                .setTitle("LogOut?")
                .setMessage("Are you sure want to LogOut")
                .setCancelable(false)
                .setPositiveButton("LogOut", R.drawable.logout) { dialogInterface, which ->
                    auth.signOut()

                    Toast.makeText(context, "LogOut", Toast.LENGTH_SHORT).show()

                    val i = Intent(getActivity() , SignInPage::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)

                    dialogInterface.dismiss()
                }
                .setNegativeButton("Cancel") { dialogInterface, which ->
                    //Toast.makeText(context, "Cancelled!", Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()
                }.build()

            // Show Dialog
            mBottomSheetDialog.show()

        }

        binding.profilefatchImage.setOnClickListener {
            startActivity(Intent(context , UploadProfileImage::class.java))
        }

        binding.profileYourProfile.setOnClickListener {

            startActivity(Intent(context , UpdateProfile::class.java))

        }

    }

}