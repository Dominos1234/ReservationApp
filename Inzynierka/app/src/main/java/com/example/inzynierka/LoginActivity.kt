package com.example.inzynierka

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson


class LoginActivity : AppCompatActivity() {
    private val REQUEST_CODE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
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

            Log.d("Success", json)

            val toast : Toast

            var gson = Gson()
            var User = gson.fromJson(data, User.UserInfo::class.java)

            if (User.code == "incorrectLogin"){
                toast = Toast.makeText(applicationContext, "Incorrect username or password, try again", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.TOP, 0, 140)
                toast.show()
            }
            else if(User.code == "correct"){
                val sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                editor.putString("Id", User.data.id)
                editor.putString("login", User.data.login)
                editor.putString("sessionId", User.data.password)
                editor.putString("externalId", User.data.externalId)
                editor.putBoolean("adminUser", User.data.adminUser)

                editor.apply()

                var login = User.data.login

                toast = Toast.makeText(applicationContext, "Welcome $login", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.TOP, 0, 140)
                toast.show()

                showResourcesActivity()
            }


        }, failure = { error ->
            Log.e("Failure", error.toString())
        })

    }
        /*
        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            val inflater = menuInflater
            inflater.inflate(com.example.inzynierka.R.menu.settings_menu_nologout, menu)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            if (item.itemId == com.example.inzynierka.R.id.settings){
                return true
            }
            else if (item.itemId == com.example.inzynierka.R.id.qr_code){
                QRScanner()
                return true
            }
            return super.onOptionsItemSelected(item)
        }

*/

        fun ScanQRCode(view: View){
            var i = Intent(this, QRScannerActivity::class.java)
            startActivity(i)
        }

        fun settings(){
            var i = Intent(this, SettingsActivity::class.java)
            startActivity(i)
        }
}

