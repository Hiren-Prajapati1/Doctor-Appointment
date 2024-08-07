package com.example.appointment.Activity

import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.appointment.FirebaseStoreDataModel.UserModel
import com.example.appointment.databinding.ActivityUploadProfileImageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.shashank.sony.fancytoastlib.FancyToast

class UploadProfileImage : AppCompatActivity() {

    lateinit var binding: ActivityUploadProfileImageBinding
    private var uri: Uri? = null
    private var storage : StorageReference? = null
    private var database : DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadProfileImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userUID = FirebaseAuth.getInstance().currentUser?.uid

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
            binding.uploadPicProfileDp.setImageURI(it)
            if (it != null) {
                uri = it
            }
        }

        binding.uploadPicChooseButton.setOnClickListener {
            pickImage.launch("image/*")
        }

        database = FirebaseDatabase.getInstance().getReference("User")
        storage = FirebaseStorage.getInstance().getReference("Profile Images")

        binding.uploadPicBtnUpload.setOnClickListener {

            val dialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            dialog.progressHelper.barColor = Color.parseColor("#A5DCB6")
            dialog.setTitleText("loading")
            dialog.setCancelable(false)
            dialog.show()

            if (userUID != null){
                if (uri != null){
                    uri?.let {
                        storage!!.child(userUID).putFile(it)
                            .addOnSuccessListener { task ->
                                task.metadata!!.reference!!.downloadUrl
                                    .addOnSuccessListener {

                                        val imageURl = uri.toString()

//                                        val map = HashMap<String , Any>()
//                                        map.put("ProfileImage" , imageURl)

                                        val FirebaseData = mapOf<String , Any>(
                                            "profileImage" to imageURl
                                        )

                                        database!!.child(userUID).updateChildren(FirebaseData)
                                            .addOnCompleteListener {

                                                dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                                                dialog.setTitleText("Success")
                                                dialog.setContentText("Profile Image Uploaded")
                                                dialog.setConfirmButton("Back") {
                                                    dialog.dismissWithAnimation()
                                                    finish()
                                                }.show()

                                            }.addOnFailureListener {
                                                Toast.makeText(this, it.localizedMessage , Toast.LENGTH_SHORT).show()
                                            }

                                    }
                            }
                    }
                }else{
                    dialog.dismissWithAnimation()
                    FancyToast.makeText(this , "Image Not Selecte" , FancyToast.LENGTH_SHORT , FancyToast.ERROR , false).show()
                }
            }else {
                dialog.dismiss()
                Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show()
            }

        }

        binding.backUploadProfilePic.setOnClickListener {
            finish()
        }

    }
}