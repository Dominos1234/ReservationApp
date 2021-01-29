package com.example.inzynierka

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import java.util.*


class LoginActivity : AppCompatActivity() {
    private val REQUEST_CODE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        val sharedPreferences2 = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language = sharedPreferences2.getString("language","en")
        setAppLocale(language, this)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT > 9) {
            val policy =
                StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        popupMenu()
    }

    private fun popupMenu(){
        val showMenuBtn:View = findViewById(R.id.showMenuBtn)
        val popupMenu = PopupMenu(this,showMenuBtn)

        popupMenu.menuInflater.inflate(R.menu.settings_menu_nologout,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.settings){
                settings()
            }
            false
        }
        showMenuBtn.setOnClickListener {
            popupMenu.show()
        }
    }
    fun showResourcesActivity(){
        val i = Intent(this, CategoriesActivity::class.java)
        startActivityForResult(i,REQUEST_CODE)
    }

    fun tryLogin(v: View){
        val username = findViewById<EditText>(R.id.username).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()

        val bodyJson = """
          { "login" : "$username",
            "password" : "$password"
          }
        """

        val (_, _, result) = Fuel.post("http://51.178.82.249:2067/api/user/login")
            .body(bodyJson)
            .responseString()

        val (data, _) = result


        result.fold(success = { json ->

            var gson = Gson()
            var User = gson.fromJson(data, User.UserInfo::class.java)

            when(User.code){
                "incorrectLogin" -> Toast.makeText(this,  resources.getString(R.string.incorrect_login), Toast.LENGTH_LONG).show()

                "correct" -> {
                val sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                editor.putString("Id", User.data.id)
                editor.putString("login", User.data.login)
                editor.putString("sessionId", User.data.password)
                editor.putString("externalId", User.data.externalId)
                editor.putBoolean("adminUser", User.data.adminUser)
                editor.apply()

                Toast.makeText(applicationContext,resources.getString(R.string.welcome) + " " + "${User.data.login}",Toast.LENGTH_LONG).show()
                showResourcesActivity()
                }
                "unknownError" -> Toast.makeText(this,  resources.getString(R.string.unknown_error), Toast.LENGTH_LONG).show()
            }

        }, failure = { error ->
            Log.e("Failure", error.toString())
        })

    }


    fun ScanQRCode(view: View){
        var i = Intent(this, QRScannerActivity::class.java)
        startActivity(i)
    }

    fun settings(){
        var i = Intent(this, SettingsActivity::class.java)
        i.putExtra("activity","log")
        startActivity(i)
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
}


