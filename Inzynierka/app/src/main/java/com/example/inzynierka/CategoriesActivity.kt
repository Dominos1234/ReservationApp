package com.example.inzynierka

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Base64
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson


class CategoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        setContentView(com.example.inzynierka.R.layout.activity_categories)
        if (Build.VERSION.SDK_INT > 9) {
            val policy =
                StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        popupMenu()

        var sessionId :String
        val sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        sessionId = sharedPreferences.getString("sessionId","").toString()


        val (_, _, result) = Fuel.get("http://51.178.82.249:2067/api/user/category")
            .header("session-id", sessionId)
            .responseString()

        val (data, _) = result


        result.fold(success = { json ->


            var toast : Toast

            var gson = Gson()
            var CategoriesJson = gson.fromJson(data, CategoriesJson.CategoryInfo::class.java)

            Log.d("Success", json)

            var notLogged = "Sorry, something went wrong, you were disconnected"
            var missingPermissions = "Account permissions are not sufficient to perform this action."
            var unknownError = "Unknown error occurred."

            when (CategoriesJson.code){ //send back to login
                "notLogged" -> {
                    Logout()
                    Toast.makeText(this, notLogged, Toast.LENGTH_LONG).show()
                }
                "correct" -> {
                    var categories = ArrayList<Category>()

                    for (category in CategoriesJson.data) {
                        var cat = Category()
                        cat.id = category.id
                        cat.description = category.description
                        cat.name = category.name
                        cat.attributes = category.attributes
                        cat.imageBase64 = category.imageBase64

                        categories.add(cat)
                    }
                    Log.d("Success", categories.get(0).imageBase64)

                    showCategories(categories)
                }

                "missingPermissions" -> Toast.makeText(this, missingPermissions, Toast.LENGTH_LONG).show()
                "unknownError" -> Toast.makeText(this, unknownError, Toast.LENGTH_LONG).show()
            }

        }, failure = { error ->
            Log.e("Failure", error.toString())
        })


    }
/*
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
            Logout()
            return true
        }
        else if (item.itemId == com.example.inzynierka.R.id.qr_code){
            QRScanner()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

 */

    fun popupMenu(){
        val showMenuBtn:View = findViewById(R.id.showMenuBtn)
        val popupMenu = PopupMenu(this,showMenuBtn)

        popupMenu.menuInflater.inflate(R.menu.settings_menu,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            if (item.itemId == com.example.inzynierka.R.id.settings){

            }
            else if (item.itemId == com.example.inzynierka.R.id.logout){
                Logout()
            }
            else if (item.itemId == com.example.inzynierka.R.id.qr_code){
                QRScanner()
            }

            false
        }
        showMenuBtn.setOnClickListener {
            popupMenu.show()
        }
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

    fun Logout(){
        clearSharedPreferences()
        var i = Intent(this, LoginActivity::class.java)
        startActivity(i)
    }

    fun QRScanner(){
        var i = Intent(this, QRScannerActivity::class.java)
        startActivity(i)
    }

    private fun showCategories(categories: ArrayList<Category>){
        val listView = findViewById<ListView>(com.example.inzynierka.R.id.categories_listview)
        val adapter = MyCustomAdapter(this, categories)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val element = adapter.getItemId(position) // The item that was clicked

            val sharedPreferences = getSharedPreferences("Category", Context.MODE_PRIVATE) //Saving chosen element
            val editor = sharedPreferences.edit()

            editor.putString("Id", categories.get(element.toInt()).id)
            editor.apply()

            val intent = Intent(this, SplashScreen2Activity::class.java)
            startActivity(intent)
        }

    }

    private class MyCustomAdapter(context: Context, itemList: ArrayList<Category>) : BaseAdapter()
    {
        private val mContext: Context
        private val mItemList: ArrayList<Category>

        init{
            mContext = context
            mItemList = itemList
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(com.example.inzynierka.R.layout.categories_row,parent, false)

            val Name = rowMain.findViewById<TextView>(com.example.inzynierka.R.id.name)
            val Description = rowMain.findViewById<TextView>(com.example.inzynierka.R.id.description)
            val Image = rowMain.findViewById<ImageView>(com.example.inzynierka.R.id.image)


            Name.text = mItemList.get(position).name
            Description.text = mItemList.get(position).description

            val cleanImage = mItemList.get(position).imageBase64.replace("data:image/png;base64,", "")
                .replace("data:image/jpeg;base64,", "")
            val imageBytes = Base64.decode(cleanImage, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            Image.setImageBitmap(decodedImage)

            return rowMain
        }

        override fun getItem(position: Int): Any {
            return "String"
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return mItemList.size
        }

    }
}
