package com.example.appointment.LoginActivity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import com.example.appointment.HomePage
import com.example.appointment.R
import com.example.appointment.databinding.ActivitySignInPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.shashank.sony.fancytoastlib.FancyToast

class SignInPage : AppCompatActivity() {

    lateinit var binding: ActivitySignInPageBinding
    lateinit var auth : FirebaseAuth


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        // for the show and hide the password using eye image
        binding.showHidePasswordSignIn.setImageResource(R.drawable.password_eye_hide)
        binding.showHidePasswordSignIn.setOnClickListener(View.OnClickListener {
            if (binding.passwordSignIn.getTransformationMethod().equals(
                    HideReturnsTransformationMethod.getInstance())) {
                // if password is visible the hide it
                binding.passwordSignIn.setTransformationMethod(PasswordTransformationMethod.getInstance())
                binding.showHidePasswordSignIn.setImageResource(R.drawable.password_eye_hide)
            } else {
                binding.passwordSignIn.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
                binding.showHidePasswordSignIn.setImageResource(R.drawable.password_eye_show)
            }
        })


        binding.btnSignIn.setOnClickListener{

            val email = binding.emailSignIn.text.toString()
            val password = binding.passwordSignIn.text.toString()

            // -->  to check for the Email Address are invalid or not
            binding.emailSignIn.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {  }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {  }

                override fun afterTextChanged(p0: Editable?) {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailSignIn.text.toString()).matches())
                        binding.btnSignIn.isEnabled = true
                    else{
                        binding.btnSignIn.isEnabled = false
                        binding.emailSignIn.setError("Invalid Email")
                    }
                }

            })

            // --> this function is used for login Activity
            if (email.isNotEmpty() && password.isNotEmpty()){
                binding.progressBarSignIn.visibility = View.VISIBLE

                auth.signInWithEmailAndPassword(email , password).addOnCompleteListener {

                    if (it.isSuccessful){
                        binding.progressBarSignIn.visibility = View.GONE
                        FancyToast.makeText(this , "Successfully Login" , FancyToast.LENGTH_LONG , FancyToast.SUCCESS , false).show()

                        val intent : Intent = Intent(this@SignInPage, HomePage::class.java)
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener{
                    binding.progressBarSignIn.visibility = View.GONE
                    Toast.makeText(this, "Email Or Password are Incoorect", Toast.LENGTH_LONG).show()
                }
            }
            else if (email.isEmpty() && password.isEmpty()){ FancyToast.makeText(this , "Please enter email and password" , FancyToast.LENGTH_LONG , FancyToast.ERROR , false).show()
            }else if (email.isEmpty()) { FancyToast.makeText(this, "Please enter email", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show()
            }else if (password.isEmpty()) { FancyToast.makeText(this, "Please enter password", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show() }

        }

        binding.textSignUp.setOnClickListener {
            val inex : Intent = Intent(this@SignInPage , SignUpPage::class.java)
            startActivity(inex)
        }
    }


    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this@SignInPage, HomePage::class.java))
            finish()
        }
    }
}