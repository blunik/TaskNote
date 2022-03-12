package ru.com.tasknote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.com.tasknote.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}