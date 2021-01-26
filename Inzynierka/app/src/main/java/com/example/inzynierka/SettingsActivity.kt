package com.example.inzynierka

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    fun polish(v: View){
        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("language", "polish")
        editor.apply()
        Toast.makeText(this,"Wybrany jezyk: Polski",Toast.LENGTH_SHORT).show()
    }
    fun english(v: View){
        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("language", "english")
        editor.apply()
        Toast.makeText(this,"Chosen language: English",Toast.LENGTH_SHORT).show()
    }

}
