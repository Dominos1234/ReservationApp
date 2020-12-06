package com.example.inzynierka

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.json.responseJson
import kotlin.reflect.typeOf


class MainActivity : AppCompatActivity() {
    val REQUEST_CODE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun showResourcesActivity(){
        val i = Intent(this, ResourcesActivity::class.java)
        startActivityForResult(i,REQUEST_CODE)
    }

    fun tryLogin(v: View){
        val username = findViewById<EditText>(R.id.username).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()


        var toast = Toast.makeText(applicationContext, "$password", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP, 0, 140)
        toast.show()


        val bodyJson = """
          { "login" : "arek",
            "password" : "arek"
          }
        """
        val requestParams: MutableList<Pair<String, String>> = ArrayList(2)
        requestParams.add(Pair("login", username))
        requestParams.add(Pair("password", password))


        val (request, response, result) = Fuel.post("https://51.178.82.249:2067/api/user/login")
            .body(bodyJson)
            .responseJson()

        var myData = ""
        result.fold(success = {
            myData = it.toString()
        }, failure = {
            myData = it.errorData.toString()
        })

        val (data, error) = result

        val jsonArray = data?.array()

        Log.e("cos", error.toString())
        toast = Toast.makeText(applicationContext, "Welcome $jsonArray!", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP, 0, 140)
        toast.show()
        showResourcesActivity()



        }
}

