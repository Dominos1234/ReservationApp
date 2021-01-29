package com.example.inzynierka

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import java.util.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        loadLocale()
        setContentView(R.layout.activity_settings)

        val polish = findViewById<ImageView>(R.id.polish)
        val english = findViewById<ImageView>(R.id.english)

        polish.bringToFront()
        polish.setOnClickListener {
            polish(it)
        }
        english.bringToFront()
        english.setOnClickListener {
            english(it)
        }
    }

    fun polish(v: View){

        setLocale("pl")
        Toast.makeText(this,resources.getString(R.string.polish),Toast.LENGTH_SHORT).show()
        recreate()
    }
    fun english(v: View){

        setLocale("en")
        Toast.makeText(this,resources.getString(R.string.english),Toast.LENGTH_SHORT).show()
        recreate()
    }


    fun setAppLocale(languageFromPreference: String?, context: Context)
    {

        if (languageFromPreference != null) {
            val resources: Resources = context.resources
            val dm: DisplayMetrics = resources.displayMetrics
            val config: Configuration = resources.configuration
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLocale(Locale(languageFromPreference.toLowerCase(Locale.ROOT)))
            } else {
                config.setLocale(Locale(languageFromPreference.toLowerCase(Locale.ROOT)))
            }
            resources.updateConfiguration(config, dm)
        }
    }

    fun setLocale(Lang: String){
        val locale = Locale(Lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("language", Lang)
        editor.apply()

    }

    fun loadLocale(){
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("language","en")
        if (language != null) {
            setLocale(language)
        }
    }

    fun save(v:View){
        val goTo = this@SettingsActivity.intent.getStringExtra("activity")
        var i: Intent? = null
        when (goTo) {
            "log" -> i = Intent(this, LoginActivity::class.java)
            "cat" -> i = Intent(this, CategoriesActivity::class.java)
            "item" -> i = Intent(this, ItemsActivity::class.java)
        }
        startActivity(i)
    }

}
