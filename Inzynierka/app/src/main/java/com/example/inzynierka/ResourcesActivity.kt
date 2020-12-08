package com.example.inzynierka

import android.R
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers


class ResourcesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.inzynierka.R.layout.activity_resources)
        if (Build.VERSION.SDK_INT > 9) {
            val policy =
                StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        var sessionId :String
        val sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        sessionId = sharedPreferences.getString("sessionId","").toString()

        val (_, _, result) = Fuel.get("http://51.178.82.249:2067/api/user/category")
            .header("sessionId", sessionId)
            .responseString()

        val (data, _) = result


        result.fold(success = { json ->

            Log.d("Success2", sessionId)
            Log.d("Success", data)

            val toast = Toast.makeText(applicationContext, "Welcome $data!", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.TOP, 0, 140)
            toast.show()


        }, failure = { error ->
            Log.e("Failure", error.toString())
        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(com.example.inzynierka.R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == com.example.inzynierka.R.id.settings){
            return true
        }
        else if (item.itemId == com.example.inzynierka.R.id.logout){
            clearSharedPreferences()
            var i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun clearSharedPreferences(){
        val sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("Id","")
        editor.putString("login","")
        editor.putString("sessionId","")
        editor.putInt("externalId",0)
        editor.putBoolean("adminUser", false)

        editor.apply()
    }
}
