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
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject




class Response(json: String) : JSONObject(json) {
    val type: String? = this.optString("type")
    val data = this.optJSONArray("data")
        ?.let { 0.until(it.length()).map { i -> it.optJSONObject(i) } } // returns an array of JSONObject
        ?.map { Foo(it.toString()) } // transforms each JSONObject of the array into Foo
}

class Foo(json: String) : JSONObject(json) {
    val id = this.optInt("id")
    val title: String? = this.optString("title")
}

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
          { "login" : "$username",
            "password" : "$password"
          }
        """

        val (request, response, result) = Fuel.post("http://51.178.82.249:2067/api/user/login")
            //.header("Content-Type", "application/json")
            //.header(username,password)
            .body(bodyJson)
            .responseString()

        val (data, error) = result

        var login = ""

        result.fold(success = { json ->
            var gson = Gson()
            var User = gson.fromJson(data, User.UserInfo::class.java)
            var login = User.data.login

            Log.d("Success", login)

            val toast = Toast.makeText(applicationContext, "Welcome $login!", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.TOP, 0, 140)
            toast.show()

            showResourcesActivity()

        }, failure = { error ->
            Log.e("Failure", error.toString())
        })










        }
}

