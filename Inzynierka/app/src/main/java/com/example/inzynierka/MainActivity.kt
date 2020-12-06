package com.example.inzynierka

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    val REQUEST_CODE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT > 9) {
            val policy =
                StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

    }

    fun showResourcesActivity(){
        val i = Intent(this, ResourcesActivity::class.java)
        startActivityForResult(i,REQUEST_CODE)
    }

    fun tryLogin(v: View){
        val username = findViewById<EditText>(R.id.username).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()



        val bodyJson = """
          { "login" : "arek",
            "password" : "arek"
          }
        """


        val json = JSONObject()
        json.put("login", "arek")
        json.put("password", "arek")

        val (request, response, result) = Fuel.post("http://51.178.82.249:2067/api/user/login")
            //.header("Content-Type", "application/json")
            //.header(username,password)
            .body(bodyJson)
            .responseJson()

        result.fold(success = { json ->
            Log.d("Success", json.array().toString())
        }, failure = { error ->
            Log.e("Failure", error.toString())
        })

        val (data, error) = result

        val jsonArray = data?.array()

        val toast = Toast.makeText(applicationContext, "Welcome $jsonArray!", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP, 0, 140)
        toast.show()
        showResourcesActivity()



        }
}

