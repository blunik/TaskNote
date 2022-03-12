package ru.com.tasknote.presentation.screen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import ru.com.tasknote.MainActivity
import ru.com.tasknote.R

class SplashScreen : AppCompatActivity() {

    private val time = 3000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        splashScreenTimer()
    }

    private fun splashScreenTimer() {
        Handler(Looper.getMainLooper()).postDelayed(
            { intentProfile() }, time
        )
    }

    private fun intentProfile() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}