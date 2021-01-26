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

    lateinit var category: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.activity_items)
        if (Build.VERSION.SDK_INT > 9) {
            val policy =
                StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        category = intent.getSerializableExtra("category") as Category

        popupMenu()

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


        result.fold(success = { _ ->

            var gson = Gson()
            var categoryJson = gson.fromJson(data, CategoryJson.CategoryInfo::class.java)


            var notLogged = "Sorry, something went wrong, you were disconnected"
            var missingPermissions = "Account permissions are not sufficient to perform this action."
            var unknownError = "Unknown error occurred."

            when (categoryJson.code) { //send back to login
                "notLogged" -> {
                    Logout()
                    Toast.makeText(this, notLogged, Toast.LENGTH_LONG).show()
                }
                "correct" -> {
                    var items = ArrayList<Item>()

                    for (i in categoryJson.data.items) {
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


    fun popupMenu(){
        val showMenuBtn:View = findViewById(R.id.showMenuBtn)
        val popupMenu = PopupMenu(this,showMenuBtn)

        popupMenu.menuInflater.inflate(R.menu.settings_menu,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            if (item.itemId == com.example.inzynierka.R.id.settings){
                settings()
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

    private fun settings(){
        var i = Intent(this, SettingsActivity::class.java)
        startActivity(i)
    }

    private fun Logout(){
        clearSharedPreferences()
        var i = Intent(this, LoginActivity::class.java)
        startActivity(i)
    }

    private fun QRScanner(){
        var i = Intent(this, QRScannerActivity::class.java)
        startActivity(i)
    }

    private fun showItems(items: ArrayList<Item>){
        val listView = findViewById<ListView>(com.example.inzynierka.R.id.items_listview)
        val adapter = MyItemAdapter(this, items, category)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, CalendarActivity::class.java)
            intent.putExtra("item", items.get(position) as Serializable)
            startActivity(intent)
        }
    }

    private class MyItemAdapter(context: Context, itemList: ArrayList<Item>, category: Category) : BaseAdapter()
    {
        private val mContext: Context
        private val mItemList: ArrayList<Item>
        private val mCategory: Category

        init{
            mContext = context
            mItemList = itemList
            mCategory = category
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(com.example.inzynierka.R.layout.items_row,parent, false)

            val Name = rowMain.findViewById<TextView>(com.example.inzynierka.R.id.name)
            val Description = rowMain.findViewById<TextView>(com.example.inzynierka.R.id.description)
            val Image = rowMain.findViewById<ImageView>(com.example.inzynierka.R.id.image)

            val nameVal1 = rowMain.findViewById<TextView>(com.example.inzynierka.R.id.nameVal1)
            val nameVal2 = rowMain.findViewById<TextView>(com.example.inzynierka.R.id.nameVal2)
            val nameVal3 = rowMain.findViewById<TextView>(com.example.inzynierka.R.id.nameVal3)
            val nameVal4 = rowMain.findViewById<TextView>(com.example.inzynierka.R.id.nameVal4)
            val nameVal5 = rowMain.findViewById<TextView>(com.example.inzynierka.R.id.nameVal5)
            val nameVal6 = rowMain.findViewById<TextView>(com.example.inzynierka.R.id.nameVal6)
            val nameVal7 = rowMain.findViewById<TextView>(com.example.inzynierka.R.id.nameVal7)

            var nameValList = java.util.ArrayList<TextView>()
            nameValList.add(nameVal1)
            nameValList.add(nameVal2)
            nameValList.add(nameVal3)
            nameValList.add(nameVal4)
            nameValList.add(nameVal5)
            nameValList.add(nameVal6)
            nameValList.add(nameVal7)

            var i = 0
            for (attributeVal in mItemList.get(position).attributes ){
                val id = attributeVal.id
                val value = attributeVal.value
                var nameVal = ""
                for(attribute in mCategory.attributes!!){
                    if(attribute.id==id){
                        nameVal = "${attribute.name}: $value"
                    }
                }
                nameValList[i].text = nameVal
                i++
            }


            Name.text = "Nazwa: " + mItemList.get(position).name
            Description.text = "Opis: " + mItemList.get(position).description


            if (mItemList.get(position).imagesBase64.isNotEmpty()) {
                val cleanImage = mItemList.get(position).imagesBase64.get(0)
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

    private class MyAttributeAdapter(context: Context, attributeList: ArrayList<AttributeValue>, category: Category) : BaseAdapter()
    {
        private val mContext: Context
        private val mAttributeList: ArrayList<AttributeValue>
        private val mCategory: Category

        init{
            mContext = context
            mAttributeList = attributeList
            mCategory = category
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(com.example.inzynierka.R.layout.attribute_row,parent, false)

            val Name = rowMain.findViewById<TextView>(com.example.inzynierka.R.id.name)
            val Value = rowMain.findViewById<TextView>(com.example.inzynierka.R.id.value)


            val id = mAttributeList.get(position).id
            var name = ""
            for(attribute in mCategory.attributes!!){
                if(attribute.id==id){
                    name = attribute.name + ":"
                }
            }

            Name.text = name
            Value.text = mAttributeList.get(position).value

            return rowMain
        }

        override fun getItem(position: Int): Any {
            return "String"
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return mAttributeList.size
        }

        override fun isEnabled(position: Int): Boolean {
            return false
        }

    }
}



