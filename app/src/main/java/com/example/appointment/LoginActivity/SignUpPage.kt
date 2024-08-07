package com.example.appointment.LoginActivity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appointment.FirebaseStoreDataModel.UserModel
import com.example.appointment.R
import com.example.appointment.databinding.ActivitySignUpPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shashank.sony.fancytoastlib.FancyToast
import java.util.regex.Pattern


class SignUpPage : AppCompatActivity() {

    lateinit var binding: ActivitySignUpPageBinding
    lateinit var auth : FirebaseAuth
    var database : DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        // for the show and hide the password using eye image
        binding.showHidePasswordSignUp.setImageResource(R.drawable.password_eye_hide)
        binding.showHidePasswordSignUp.setOnClickListener(View.OnClickListener {
            if (binding.passwordSignUp.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                // if password is visible the hide it
                binding.passwordSignUp.setTransformationMethod(PasswordTransformationMethod.getInstance())
                binding.showHidePasswordSignUp.setImageResource(R.drawable.password_eye_hide)
            } else {
                binding.passwordSignUp.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
                binding.showHidePasswordSignUp.setImageResource(R.drawable.password_eye_show)
            }
        })

        // for the show and hide the Conform password using eye image
        binding.showHideConformPassword.setImageResource(R.drawable.password_eye_hide)
        binding.showHideConformPassword.setOnClickListener(View.OnClickListener {
            if (binding.conformPasswordSignUp.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                // if password is visible the hide it
                binding.conformPasswordSignUp.setTransformationMethod(PasswordTransformationMethod.getInstance())
                binding.showHideConformPassword.setImageResource(R.drawable.password_eye_hide)
            } else {
                binding.conformPasswordSignUp.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
                binding.showHideConformPassword.setImageResource(R.drawable.password_eye_show)
            }
        })


        // -->  to check for the Email Address are invalid or not
        binding.emailSignUp.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {  }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {  }

            override fun afterTextChanged(p0: Editable?) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailSignUp.text.toString()).matches())
                    binding.btnSignUp.isEnabled = true
                else{
                    binding.btnSignUp.isEnabled = false
                    binding.emailSignUp.setError("Invalid Email")
                }
            }

        })

        // check for the password validation
        binding.passwordSignUp.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {  }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {  }

            override fun afterTextChanged(p0: Editable?) {
                val password = binding.passwordSignUp.text.toString()
                if (password.length >= 6  &&  password.matches(".*[A-Z].*".toRegex()) && password.matches(".*[a-z].*".toRegex()) &&
                    password.matches(".*[!@#$%^&*?/`~_+-].*".toRegex()) && password.matches(".*[0-9].*".toRegex())){

                    binding.btnSignUp.isEnabled = true

                }else{
                    binding.btnSignUp.isEnabled = false
                    binding.passwordSignUp.setError("Minimum 6 Character  password and Atlist 1  Uppercase, 1  Lowercase, 1  Numeric, 1  Special character")
                }
            }

        })

        // --> to check the phonr number is 10 digit or not . the number is not to same as 10 digit it throw the error
        binding.numberSignUp.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {  }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {  }

            override fun afterTextChanged(p0: Editable?) {

                val mobileRegex = "[6-9][0-9]{9}"
                val mobilePattern = Pattern.compile(mobileRegex)
                val mobileMatcher = mobilePattern.matcher(binding.numberSignUp.text.toString())

                if (!mobileMatcher.find() && binding.numberSignUp.length()==10) {
                    binding.btnSignUp.isEnabled = false
                    binding.numberSignUp.setError("Invalid Phone Number")
                }else{
                    binding.btnSignUp.isEnabled = true
                }
//                if (binding.numberSignUp.length() == 10){
//                    if (binding.numberSignUp.text[0] >= 6.toChar()){
//
//                    }
//                    else{
//                        binding.btnSignUp.isEnabled = false
//                        binding.numberSignUp.setError("Invalid Phone Number")
//                    }
//                } else{
//                    binding.btnSignUp.isEnabled = false
//                    binding.numberSignUp.setError("Invalid Phone Number")
//                }
            }

        })


        binding.btnSignUp.setOnClickListener{

            val name = binding.nameSignUp.text.toString().trim()
            val email = binding.emailSignUp.text.toString().trim()
            val number = binding.numberSignUp.text.toString().trim()
            val password = binding.passwordSignUp.text.toString().trim()
            val Conformpassword = binding.conformPasswordSignUp.text.toString().trim()

            // this is for the check any field empty or notthrow empty field
            if (name.isEmpty()){
                binding.nameSignUp.error = "Username Required"
                return@setOnClickListener
            }else if (email.isEmpty()){
                binding.emailSignUp.error = "Email Required"
                return@setOnClickListener
            } else if (number.isEmpty()){
                binding.numberSignUp.error = "PhoneNumber Required"
                return@setOnClickListener
            }else if (password.isEmpty()){
                binding.passwordSignUp.error = "Password Required"
                return@setOnClickListener
            }else if (Conformpassword.isEmpty()){
                binding.conformPasswordSignUp.error = "Enter Conform Password"
                return@setOnClickListener
            }else if (binding.checkBoxSignUp.isChecked ){

            }else{
                FancyToast.makeText(this , "Please, agree Terms and Condition" , FancyToast.LENGTH_LONG , FancyToast.ERROR , false).show()
                return@setOnClickListener
            }


            binding.progressBarSignUp.visibility = View.VISIBLE


            if (email.isNotEmpty() && password.isNotEmpty() && Conformpassword.isNotEmpty()){
                if (password == Conformpassword){

                    auth.createUserWithEmailAndPassword(email , password).addOnCompleteListener {

                        if (it.isSuccessful){

                            val userUID = auth.currentUser?.uid

                            val userModel = UserModel(name , email , number , password)
                            database = FirebaseDatabase.getInstance().getReference("User")

                            database!!.child(userUID!!).setValue(userModel).addOnCompleteListener {

                                binding.progressBarSignUp.visibility = View.GONE
                                FancyToast.makeText(this , "Successfully Register" , FancyToast.LENGTH_LONG , FancyToast.SUCCESS , false).show()

                                val intent = Intent(this , SignInPage::class.java)
                                startActivity(intent)
                                finish()

                            }.addOnFailureListener {

                                Toast.makeText(this, "database error : " + it.localizedMessage , Toast.LENGTH_SHORT).show()
                            }

                        }
//                        else{
//                            Toast.makeText(this, it.exception.toString() , Toast.LENGTH_LONG).show()
//                        }
                    }.addOnFailureListener{
                        binding.progressBarSignUp.visibility = View.GONE
                        Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                    }
                }
                else{
                    binding.progressBarSignUp.visibility = View.GONE
                    FancyToast.makeText(this , "conform Password Not Matched" , FancyToast.LENGTH_SHORT , FancyToast.ERROR , false).show()
                }
            }
//            else{
//                FancyToast.makeText(this , "Fields are empty , Fill it" , FancyToast.LENGTH_LONG , FancyToast.ERROR , false).show()
//            }

        }


        binding.textSignIn.setOnClickListener {
            val inext = Intent(this , SignInPage::class.java)
            startActivity(inext)
        }

    }
}