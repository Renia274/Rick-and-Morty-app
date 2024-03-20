package com.example.rickmorty.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.rickmorty.MainActivity
import com.example.rickmorty.R


class SplashActivity : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Use a Handler to delay the transition to the next activity
        Handler().postDelayed({
            // Start the next activity after the splash delay
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Close the splash activity so that it's not displayed when back button is pressed
        }, SPLASH_DELAY)
    }
}
