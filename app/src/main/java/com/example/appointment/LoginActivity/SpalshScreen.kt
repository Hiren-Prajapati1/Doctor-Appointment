package com.example.appointment.LoginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.appointment.R

class SpalshScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh_screen)


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SpalshScreen , SignInPage::class.java)
            startActivity(intent)
            finish()
        }, 1000)

    }
}