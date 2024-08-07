package com.example.appointment.FirebaseStoreDataModel

import com.example.appointment.Activity.UploadProfileImage

data class UserModel(
    val name : String? = null,
    val email :String? = null,
    val number : String? = null,
    val password : String? = null,
    val profileImage : String? = null
)