package com.example.inzynierka

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.ContextMenu
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


class MakeReservationActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    var chosenDate = 0
    var chosenTime = 0

    val Dates:ArrayList<EditText> = arrayListOf()
    val Times:ArrayList<EditText> = arrayListOf()

    lateinit var repeatIntervalUnit: TextView
    lateinit var startDate: EditText
    lateinit var endDate: EditText
    lateinit var repeatEndDate: EditText
    lateinit var name: EditText
    lateinit var startTime: EditText
    lateinit var endTime: EditText
    lateinit var repeatEndTime: EditText
    lateinit var repeatInterval: EditText
    lateinit var item: Item


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.activity_make_reservation)

        day = intent.getIntExtra("day",0)
        month = intent.getIntExtra("month",0)
        year = intent.getIntExtra("year",0)
        hour = intent.getIntExtra("hour",0)
        minute = intent.getIntExtra("minute",0)


        item = intent.getSerializableExtra("item") as Item
        name = findViewById<EditText>(com.example.inzynierka.R.id.Name)
        startTime = findViewById<EditText>(com.example.inzynierka.R.id.startTime)
        endTime = findViewById<EditText>(com.example.inzynierka.R.id.endTime)
        repeatEndTime = findViewById<EditText>(com.example.inzynierka.R.id.repeatEndTime)
        startDate = findViewById<EditText>(com.example.inzynierka.R.id.startDate)
        endDate = findViewById<EditText>(com.example.inzynierka.R.id.endDate)
        repeatEndDate = findViewById<EditText>(com.example.inzynierka.R.id.repeatEndDate)
        repeatInterval = findViewById<EditText>(com.example.inzynierka.R.id.repeatInterval)
        repeatIntervalUnit = findViewById(com.example.inzynierka.R.id.repeatIntervalUnit)


        val nexthour = hour+1
        val currentDate = "$day/$month/$year"
        var currentTime = ""
        var nextTime = ""
        if(minute<10) {
            currentTime = "$hour:0$minute"
            nextTime = "$nexthour:0$minute"
        }
        else {
            currentTime = "$hour:$minute"
            nextTime ="$nexthour:$minute"
        }



        startDate.setText(currentDate)
        endDate.setText(currentDate)
        startTime.setText(currentTime)
        endTime.setText(nextTime)


        Dates.add(startDate)
        Dates.add(endDate)
        Dates.add(repeatEndDate)

        Times.add(startTime)
        Times.add(endTime)
        Times.add(repeatEndTime)


        for (i in 0..Dates.size-1){
            Dates[i].setOnClickListener{
                chosenDate = i
                DatePickerDialog(this,this,year,month-1,day).show()
            }
        }

        for (i in 0..Times.size-1){
            Times[i].setOnClickListener{
                chosenTime = i
                TimePickerDialog(this,this,hour,minute, true).show()
            }
        }


        registerForContextMenu(repeatIntervalUnit)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.interval_menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_day -> {
                repeatIntervalUnit.text = "Day"
                return true
            }
            R.id.menu_week -> {
                repeatIntervalUnit.text = "Week"
                return true
            }
            R.id.menu_month -> {
                repeatIntervalUnit.text = "Month"
                return true
            }

        else -> super.onContextItemSelected(item)
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month+1
        savedYear = year

        val text = "$savedDay/$savedMonth/$savedYear"
        Dates[chosenDate].setText(text)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute
        var text = ""

        if (savedMinute<10)
            text = "$savedHour:0$savedMinute"
        else
            text = "$savedHour:$savedMinute"

        Times[chosenTime].setText(text)
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun tryBooking(v: View) {
        if (name.text.isEmpty()) {
            Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show()
            return
        }

        var count = 0
        if (repeatEndDate.text.isNotEmpty()) {
            count++
            Log.d("Success","1")
        }
        if (repeatEndTime.text.isNotEmpty()) {
            count++
            Log.d("Success","2")
        }
        if (repeatIntervalUnit.text.isNotEmpty()) {
            count++
            Log.d("Success","3")
        }
        if (repeatInterval.text.isNotEmpty()) {
            count++
            Log.d("Success","4")
        }
        Log.d("Success",count.toString())
        if (count in 1..3) {
            if (repeatEndDate.text.isEmpty()) {
                Toast.makeText(
                    this,
                    "If you want to create a repeating booking, please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            if (repeatEndTime.text.isEmpty()) {
                Toast.makeText(
                    this,
                    "If you want to create a repeating booking, please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            if (repeatIntervalUnit.text.isEmpty()) {
                Toast.makeText(
                    this,
                    "If you want to create a repeating booking, please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            if (repeatInterval.text.isEmpty()) {
                Toast.makeText(
                    this,
                    "If you want to create a repeating booking, please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
        }
        createBooking()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToDate(dateToConvert: LocalDateTime): Date? {
        return Date
            .from(
                dateToConvert.atZone(ZoneId.systemDefault())
                    .toInstant()
            )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createBooking(){
        if (Build.VERSION.SDK_INT > 9) {
            val policy =
                StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        val sharedPreferencesUser = getSharedPreferences("User", Context.MODE_PRIVATE)
        var sessionId = sharedPreferencesUser.getString("sessionId","").toString()
        var userId = sharedPreferencesUser.getString("Id", "")
        var user = "${sharedPreferencesUser.getString("externalId", "")} (${sharedPreferencesUser.getString("login", "")})"

        val sharedPreferencesCategory = getSharedPreferences("Category", Context.MODE_PRIVATE)
        var categoryId = sharedPreferencesCategory.getString("Id", "")

        var Date = startDate.text.toString()
        var DateArray = Date.split('/').toTypedArray()

        var Time = startTime.text.toString()
        var TimeArray = Time.split(':').toTypedArray()

        hour = TimeArray[0].toInt()-1
        minute = TimeArray[1].toInt()

        year = DateArray[2].toInt()
        month = DateArray[1].toInt()
        day = DateArray[0].toInt()

        var mstartTime = LocalDateTime.of(year,month,day,hour,minute,0,0)

        Date = endDate.text.toString()
        DateArray = Date.split('/').toTypedArray()

        Time = endTime.text.toString()
        TimeArray = Time.split(':').toTypedArray()

        hour = TimeArray[0].toInt()-1
        minute = TimeArray[1].toInt()

        year = DateArray[2].toInt()
        month = DateArray[1].toInt()
        day = DateArray[0].toInt()

        var mendTime = LocalDateTime.of(year,month,day,hour,minute,0,0)
        var currentDate = LocalDateTime.now()

        var mrepeatEndTime:LocalDateTime? = null
        var repeatUnit = ""


        if (repeatIntervalUnit.text.isNotEmpty()) {
            Date = repeatEndDate.text.toString()
            DateArray = Date.split('/').toTypedArray()

            Time = repeatEndTime.text.toString()
            TimeArray = Time.split(':').toTypedArray()

            hour = TimeArray[0].toInt()-1
            minute = TimeArray[1].toInt()

            year = DateArray[2].toInt()
            month = DateArray[1].toInt()
            day = DateArray[0].toInt()

            mrepeatEndTime = LocalDateTime.of(year,month,day,hour,minute,0,0)

            var repeatUnit = ""
            if (repeatIntervalUnit.text == "Week")
                repeatUnit = "w"
            else if (repeatIntervalUnit.text == "Day")
                repeatUnit = "d"
            else if (repeatIntervalUnit.text == "Month")
                repeatUnit = "m"
        }

        var bodyJson=""

        if(mrepeatEndTime!=null) {
            bodyJson = """
              {"id":"",
              "itemId": "${item?.id}",
              "categoryId":"$categoryId",
              "userId":"$userId",
              "name":"${name.text}",
              "user":"$user",
              "startTime":"$mstartTime",
              "endTime":"$mendTime",
              "creationTime":"$currentDate",
              "repeatInterval":"${repeatInterval.text}",
              "repeatIntervalUnit":"$repeatUnit",
              "repeatEndTime":"$mrepeatEndTime",
              "confirmed":false}
            """
        }
        else{
            bodyJson = """
              {"id":"",
              "itemId": "${item?.id}",
              "categoryId":"$categoryId",
              "userId":"$userId",
              "name":"${name.text}",
              "user":"$user",
              "startTime":"${mstartTime}:00.000Z",
              "endTime":"${mendTime}:00.000Z",
              "creationTime":"${currentDate}Z",
              "repeatInterval":"${repeatInterval.text}",
              "repeatIntervalUnit":"$repeatUnit",
              "repeatEndTime":null,
              "confirmed":false}
            """
        }

        val (_, _, result) = Fuel.post("http://51.178.82.249:2067/api/user/category/$categoryId/item/${item?.id}/booking")
            .header("session-id", sessionId)
            .body(bodyJson)
            .responseString()

        val (data, _) = result


        result.fold(success = { json ->


            var toast: Toast

            var gson = Gson()
            var Response = gson.fromJson(data, ResponseJson.ResponseInfo::class.java)

            Log.d("Success", json)

            var notLogged = "Sorry, something went wrong, you were disconnected"
            var correct = "Successfully created a reservation"
            var missingFields = "Technical error occurred: some fields are missing."
            var invalidData = "Technical error occurred: invalid request data."
            var entityAlreadyExists = "Entity with given key already exists."
            var entityNotExists = "Error occurred: entity not exists."
            var missingPermissions = "Account permissions are not sufficient to perform this action."
            var masterAdminNotAllowed = "Master admin is not allowed for this action. Please login to admin account."
            var bookingExistingInSameInterval = "Another booking already exists in given time interval."
            var unknownError = "Unknown error occurred."

            when (Response.code){
                "notLogged" -> {
                    Logout()
                    Toast.makeText(this, notLogged, Toast.LENGTH_LONG).show()
                }
                "correct" -> {
                    Toast.makeText(this, correct, Toast.LENGTH_LONG).show()
                    //go to Reservation
                    val intent = Intent(this, ReservationActivity::class.java)
                    intent.putExtra("day", mstartTime.dayOfMonth)
                    intent.putExtra("month", mstartTime.monthValue)
                    intent.putExtra("year", mstartTime.year)
                    intent.putExtra("item", item as Serializable)
                    startActivity(intent)
                }
                "missingFields" -> Toast.makeText(this, missingFields, Toast.LENGTH_LONG).show()
                "invalidData" -> Toast.makeText(this, invalidData, Toast.LENGTH_LONG).show()
                "entityAlreadyExists" -> Toast.makeText(this, entityAlreadyExists, Toast.LENGTH_LONG).show()
                "entityNotExists" -> Toast.makeText(this, entityNotExists, Toast.LENGTH_LONG).show()
                "missingPermissions" -> Toast.makeText(this, missingPermissions, Toast.LENGTH_LONG).show()
                "masterAdminNotAllowed" -> Toast.makeText(this, masterAdminNotAllowed, Toast.LENGTH_LONG).show()
                "bookingExistingInSameInterval" -> Toast.makeText(this, bookingExistingInSameInterval, Toast.LENGTH_LONG).show()
                "unknownError" -> Toast.makeText(this, unknownError, Toast.LENGTH_LONG).show()
            }

        }, failure = { error ->
            Log.e("Failure", error.toString())
        })
    }

    fun Logout(){
        clearSharedPreferences()
        var i = Intent(this, LoginActivity::class.java)
        startActivity(i)
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
