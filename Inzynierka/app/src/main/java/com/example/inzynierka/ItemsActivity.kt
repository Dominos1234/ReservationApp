package com.example.inzynierka

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Base64
import android.util.Log
import android.view.*
import android.widget.*
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import java.io.Serializable

class ItemsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        if (Build.VERSION.SDK_INT > 9) {
            val policy =
                StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        var sessionId : String
        var categoryId : String

        val sharedPreferencesUser = getSharedPreferences("User", Context.MODE_PRIVATE)
        sessionId = sharedPreferencesUser.getString("sessionId","").toString()

        val sharedPreferencesCategory = getSharedPreferences("Category", Context.MODE_PRIVATE)
        categoryId = sharedPreferencesCategory.getString("Id","").toString()


        val (_, _, result) = Fuel.get("http://51.178.82.249:2067/api/user/category/$categoryId")
            .header("session-id", sessionId)
            .responseString()

        val (data, _) = result


        result.fold(success = { json ->
            var toast : Toast

            var gson = Gson()
            var CategoryJson = gson.fromJson(data, CategoryJson.CategoryInfo::class.java)


            var notLogged = "Sorry, something went wrong, you were disconnected"
            var missingPermissions = "Account permissions are not sufficient to perform this action."
            var unknownError = "Unknown error occurred."

            when (CategoryJson.code) { //send back to login
                "notLogged" -> {
                    Logout()
                    Toast.makeText(this, notLogged, Toast.LENGTH_LONG).show()
                }
                "correct" -> {
                    var items = ArrayList<Item>()

                    for (i in CategoryJson.data.items) {
                        var item = Item()
                        item.id = i.id
                        item.description = i.description
                        item.name = i.name
                        item.attributes = i.attributes
                        item.imagesBase64 = i.imagesBase64
                        item.categoryId = i.categoryId
                        item.bookingAuthorizationRequired = i.bookingAuthorizationRequired
                        item.responsibleUsers = i.responsibleUsers
                        item.bookings = i.bookings

                        items.add(item)
                    }

                    showItems(items)
                }
                "missingPermissions" -> Toast.makeText(this, missingPermissions, Toast.LENGTH_LONG).show()
                "unknownError" -> Toast.makeText(this, unknownError, Toast.LENGTH_LONG).show()
            }
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
            Logout()
            return true
        }
        else if (item.itemId == com.example.inzynierka.R.id.qr_code){
            QRScanner()
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

    fun Logout(){
        clearSharedPreferences()
        var i = Intent(this, LoginActivity::class.java)
        startActivity(i)
    }

    fun QRScanner(){
        var i = Intent(this, QRScannerActivity::class.java)
        startActivity(i)
    }

    private fun showItems(items: ArrayList<Item>){
        val listView = findViewById<ListView>(com.example.inzynierka.R.id.items_listview)
        val adapter = MyCustomAdapter(this, items)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->

            val intent = Intent(this, CalendarActivity::class.java)
            intent.putExtra("item", items.get(position) as Serializable)
            startActivity(intent)
        }

    }

    private class MyCustomAdapter(context: Context, itemList: ArrayList<Item>) : BaseAdapter()
    {
        private val mContext: Context
        private val mItemList: ArrayList<Item>

        init{
            mContext = context
            mItemList = itemList
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(com.example.inzynierka.R.layout.items_row,parent, false)

            val Name = rowMain.findViewById<TextView>(com.example.inzynierka.R.id.name)
            val Description = rowMain.findViewById<TextView>(com.example.inzynierka.R.id.description)
            val Image = rowMain.findViewById<ImageView>(com.example.inzynierka.R.id.image)


            Name.text = mItemList.get(position).name
            Description.text = mItemList.get(position).description

            if (mItemList.get(position).imagesBase64!!.size>0) {
                val cleanImage = mItemList.get(position).imagesBase64!!.get(0)
                    .replace("data:image/png;base64,", "")
                    .replace("data:image/jpeg;base64,", "")
                val imageBytes = Base64.decode(cleanImage, Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                Image.setImageBitmap(decodedImage)
            }

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



