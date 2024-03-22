package com.example.rickmorty.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.rickmorty.MainActivity
import com.example.rickmorty.R
import android.media.MediaPlayer

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 3000 // 3 seconds
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Initialize the MediaPlayer with the sound file
        mediaPlayer = MediaPlayer.create(this, R.raw.hello_splash)

        // Start playing the sound
        mediaPlayer.start()

        // Use a Handler to delay the transition to the next activity
        Handler().postDelayed({
            // Stop the sound playback
            mediaPlayer.stop()
            mediaPlayer.release()
            // Start the next activity after the splash delay
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Close the splash activity so that it's not displayed when back button is pressed
        }, SPLASH_DELAY)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release the MediaPlayer resources
        mediaPlayer.release()
    }
}
