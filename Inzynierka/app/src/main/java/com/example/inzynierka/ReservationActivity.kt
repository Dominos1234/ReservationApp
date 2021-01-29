package com.example.inzynierka

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import java.io.Serializable
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


class ReservationActivity : AppCompatActivity() {



    var BookedHoursOthers : ArrayList<TextView> = arrayListOf<TextView>()
    var BookedHoursMy : ArrayList<TextView> = arrayListOf<TextView>()
    var rowList = ArrayList<TextView>()
    var mday = 0
    var mmonth = 0
    var myear = 0
    lateinit var item : Item

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.activity_reservation)



        val day = intent.getIntExtra("day",0)
        val month = intent.getIntExtra("month",0)
        val year = intent.getIntExtra("year",0)
        item = intent.getSerializableExtra("item") as Item

        updateReservations()

        mday = day
        mmonth = month
        myear = year



        val row0 = findViewById<TextView>(com.example.inzynierka.R.id.row0)
        val row1 = findViewById<TextView>(com.example.inzynierka.R.id.row1)
        val row2 = findViewById<TextView>(com.example.inzynierka.R.id.row2)
        val row3 = findViewById<TextView>(com.example.inzynierka.R.id.row3)
        val row4 = findViewById<TextView>(com.example.inzynierka.R.id.row4)
        val row5 = findViewById<TextView>(com.example.inzynierka.R.id.row5)
        val row6 = findViewById<TextView>(com.example.inzynierka.R.id.row6)
        val row7 = findViewById<TextView>(com.example.inzynierka.R.id.row7)
        val row8 = findViewById<TextView>(com.example.inzynierka.R.id.row8)
        val row9 = findViewById<TextView>(com.example.inzynierka.R.id.row9)
        val row10 = findViewById<TextView>(com.example.inzynierka.R.id.row10)
        val row11 = findViewById<TextView>(com.example.inzynierka.R.id.row11)
        val row12 = findViewById<TextView>(com.example.inzynierka.R.id.row12)
        val row13 = findViewById<TextView>(com.example.inzynierka.R.id.row13)
        val row14 = findViewById<TextView>(com.example.inzynierka.R.id.row14)
        val row15 = findViewById<TextView>(com.example.inzynierka.R.id.row15)
        val row16 = findViewById<TextView>(com.example.inzynierka.R.id.row16)
        val row17 = findViewById<TextView>(com.example.inzynierka.R.id.row17)
        val row18 = findViewById<TextView>(com.example.inzynierka.R.id.row18)
        val row19 = findViewById<TextView>(com.example.inzynierka.R.id.row19)
        val row20 = findViewById<TextView>(com.example.inzynierka.R.id.row20)
        val row21 = findViewById<TextView>(com.example.inzynierka.R.id.row21)
        val row22 = findViewById<TextView>(com.example.inzynierka.R.id.row22)
        val row23 = findViewById<TextView>(com.example.inzynierka.R.id.row23)

        val row0_1 = findViewById<TextView>(com.example.inzynierka.R.id.row0_1)
        val row1_1 = findViewById<TextView>(com.example.inzynierka.R.id.row1_1)
        val row2_1 = findViewById<TextView>(com.example.inzynierka.R.id.row2_1)
        val row3_1 = findViewById<TextView>(com.example.inzynierka.R.id.row3_1)
        val row4_1 = findViewById<TextView>(com.example.inzynierka.R.id.row4_1)
        val row5_1 = findViewById<TextView>(com.example.inzynierka.R.id.row5_1)
        val row6_1 = findViewById<TextView>(com.example.inzynierka.R.id.row6_1)
        val row7_1 = findViewById<TextView>(com.example.inzynierka.R.id.row7_1)
        val row8_1 = findViewById<TextView>(com.example.inzynierka.R.id.row8_1)
        val row9_1 = findViewById<TextView>(com.example.inzynierka.R.id.row9_1)
        val row10_1 = findViewById<TextView>(com.example.inzynierka.R.id.row10_1)
        val row11_1 = findViewById<TextView>(com.example.inzynierka.R.id.row11_1)
        val row12_1 = findViewById<TextView>(com.example.inzynierka.R.id.row12_1)
        val row13_1 = findViewById<TextView>(com.example.inzynierka.R.id.row13_1)
        val row14_1 = findViewById<TextView>(com.example.inzynierka.R.id.row14_1)
        val row15_1 = findViewById<TextView>(com.example.inzynierka.R.id.row15_1)
        val row16_1 = findViewById<TextView>(com.example.inzynierka.R.id.row16_1)
        val row17_1 = findViewById<TextView>(com.example.inzynierka.R.id.row17_1)
        val row18_1 = findViewById<TextView>(com.example.inzynierka.R.id.row18_1)
        val row19_1 = findViewById<TextView>(com.example.inzynierka.R.id.row19_1)
        val row20_1 = findViewById<TextView>(com.example.inzynierka.R.id.row20_1)
        val row21_1 = findViewById<TextView>(com.example.inzynierka.R.id.row21_1)
        val row22_1 = findViewById<TextView>(com.example.inzynierka.R.id.row22_1)
        val row23_1 = findViewById<TextView>(com.example.inzynierka.R.id.row23_1)

        val row0_2 = findViewById<TextView>(com.example.inzynierka.R.id.row0_2)
        val row1_2 = findViewById<TextView>(com.example.inzynierka.R.id.row1_2)
        val row2_2 = findViewById<TextView>(com.example.inzynierka.R.id.row2_2)
        val row3_2 = findViewById<TextView>(com.example.inzynierka.R.id.row3_2)
        val row4_2 = findViewById<TextView>(com.example.inzynierka.R.id.row4_2)
        val row5_2 = findViewById<TextView>(com.example.inzynierka.R.id.row5_2)
        val row6_2 = findViewById<TextView>(com.example.inzynierka.R.id.row6_2)
        val row7_2 = findViewById<TextView>(com.example.inzynierka.R.id.row7_2)
        val row8_2 = findViewById<TextView>(com.example.inzynierka.R.id.row8_2)
        val row9_2 = findViewById<TextView>(com.example.inzynierka.R.id.row9_2)
        val row10_2 = findViewById<TextView>(com.example.inzynierka.R.id.row10_2)
        val row11_2 = findViewById<TextView>(com.example.inzynierka.R.id.row11_2)
        val row12_2 = findViewById<TextView>(com.example.inzynierka.R.id.row12_2)
        val row13_2 = findViewById<TextView>(com.example.inzynierka.R.id.row13_2)
        val row14_2 = findViewById<TextView>(com.example.inzynierka.R.id.row14_2)
        val row15_2 = findViewById<TextView>(com.example.inzynierka.R.id.row15_2)
        val row16_2 = findViewById<TextView>(com.example.inzynierka.R.id.row16_2)
        val row17_2 = findViewById<TextView>(com.example.inzynierka.R.id.row17_2)
        val row18_2 = findViewById<TextView>(com.example.inzynierka.R.id.row18_2)
        val row19_2 = findViewById<TextView>(com.example.inzynierka.R.id.row19_2)
        val row20_2 = findViewById<TextView>(com.example.inzynierka.R.id.row20_2)
        val row21_2 = findViewById<TextView>(com.example.inzynierka.R.id.row21_2)
        val row22_2 = findViewById<TextView>(com.example.inzynierka.R.id.row22_2)
        val row23_2 = findViewById<TextView>(com.example.inzynierka.R.id.row23_2)

        val row0_3 = findViewById<TextView>(com.example.inzynierka.R.id.row0_3)
        val row1_3 = findViewById<TextView>(com.example.inzynierka.R.id.row1_3)
        val row2_3 = findViewById<TextView>(com.example.inzynierka.R.id.row2_3)
        val row3_3 = findViewById<TextView>(com.example.inzynierka.R.id.row3_3)
        val row4_3 = findViewById<TextView>(com.example.inzynierka.R.id.row4_3)
        val row5_3 = findViewById<TextView>(com.example.inzynierka.R.id.row5_3)
        val row6_3 = findViewById<TextView>(com.example.inzynierka.R.id.row6_3)
        val row7_3 = findViewById<TextView>(com.example.inzynierka.R.id.row7_3)
        val row8_3 = findViewById<TextView>(com.example.inzynierka.R.id.row8_3)
        val row9_3 = findViewById<TextView>(com.example.inzynierka.R.id.row9_3)
        val row10_3 = findViewById<TextView>(com.example.inzynierka.R.id.row10_3)
        val row11_3 = findViewById<TextView>(com.example.inzynierka.R.id.row11_3)
        val row12_3 = findViewById<TextView>(com.example.inzynierka.R.id.row12_3)
        val row13_3 = findViewById<TextView>(com.example.inzynierka.R.id.row13_3)
        val row14_3 = findViewById<TextView>(com.example.inzynierka.R.id.row14_3)
        val row15_3 = findViewById<TextView>(com.example.inzynierka.R.id.row15_3)
        val row16_3 = findViewById<TextView>(com.example.inzynierka.R.id.row16_3)
        val row17_3 = findViewById<TextView>(com.example.inzynierka.R.id.row17_3)
        val row18_3 = findViewById<TextView>(com.example.inzynierka.R.id.row18_3)
        val row19_3 = findViewById<TextView>(com.example.inzynierka.R.id.row19_3)
        val row20_3 = findViewById<TextView>(com.example.inzynierka.R.id.row20_3)
        val row21_3 = findViewById<TextView>(com.example.inzynierka.R.id.row21_3)
        val row22_3 = findViewById<TextView>(com.example.inzynierka.R.id.row22_3)
        val row23_3 = findViewById<TextView>(com.example.inzynierka.R.id.row23_3)

        rowList.add(row0)
        rowList.add(row0_1)
        rowList.add(row0_2)
        rowList.add(row0_3)
        rowList.add(row1)
        rowList.add(row1_1)
        rowList.add(row1_2)
        rowList.add(row1_3)
        rowList.add(row2)
        rowList.add(row2_1)
        rowList.add(row2_2)
        rowList.add(row2_3)
        rowList.add(row3)
        rowList.add(row3_1)
        rowList.add(row3_2)
        rowList.add(row3_3)
        rowList.add(row4)
        rowList.add(row4_1)
        rowList.add(row4_2)
        rowList.add(row4_3)
        rowList.add(row5)
        rowList.add(row5_1)
        rowList.add(row5_2)
        rowList.add(row5_3)
        rowList.add(row6)
        rowList.add(row6_1)
        rowList.add(row6_2)
        rowList.add(row6_3)
        rowList.add(row7)
        rowList.add(row7_1)
        rowList.add(row7_2)
        rowList.add(row7_3)
        rowList.add(row8)
        rowList.add(row8_1)
        rowList.add(row8_2)
        rowList.add(row8_3)
        rowList.add(row9)
        rowList.add(row9_1)
        rowList.add(row9_2)
        rowList.add(row9_3)
        rowList.add(row10)
        rowList.add(row10_1)
        rowList.add(row10_2)
        rowList.add(row10_3)
        rowList.add(row11)
        rowList.add(row11_1)
        rowList.add(row11_2)
        rowList.add(row11_3)
        rowList.add(row12)
        rowList.add(row12_1)
        rowList.add(row12_2)
        rowList.add(row12_3)
        rowList.add(row13)
        rowList.add(row13_1)
        rowList.add(row13_2)
        rowList.add(row13_3)
        rowList.add(row14)
        rowList.add(row14_1)
        rowList.add(row14_2)
        rowList.add(row14_3)
        rowList.add(row15)
        rowList.add(row15_1)
        rowList.add(row15_2)
        rowList.add(row15_3)
        rowList.add(row16)
        rowList.add(row16_1)
        rowList.add(row16_2)
        rowList.add(row16_3)
        rowList.add(row17)
        rowList.add(row17_1)
        rowList.add(row17_2)
        rowList.add(row17_3)
        rowList.add(row18)
        rowList.add(row18_1)
        rowList.add(row18_2)
        rowList.add(row18_3)
        rowList.add(row19)
        rowList.add(row19_1)
        rowList.add(row19_2)
        rowList.add(row19_3)
        rowList.add(row20)
        rowList.add(row20_1)
        rowList.add(row20_2)
        rowList.add(row20_3)
        rowList.add(row21)
        rowList.add(row21_1)
        rowList.add(row21_2)
        rowList.add(row21_3)
        rowList.add(row22)
        rowList.add(row22_1)
        rowList.add(row22_2)
        rowList.add(row22_3)
        rowList.add(row23)
        rowList.add(row23_1)
        rowList.add(row23_2)
        rowList.add(row23_3)


        for (i in 0..rowList.size-1){
            if (i%4==0) {
                rowList[i].text = (i/4).toString() + ":00 - " + (i/4).toString() + ":15 "
            }
        }
        for (book in item.bookings){

            var startDate = ToLocalDateTime(book.startTime)
            var endDate = ToLocalDateTime(book.endTime)

            val sHour = startDate.hour
            var sMinute: Int = startDate.minute
            val eHour = endDate.hour
            var eMinute: Int = endDate.minute
            sMinute /= 15
            eMinute /= 15

            val only_user = book.user.split("(")[0]
            if(book.repeatInterval == "") {

                val sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
                val username = sharedPreferences.getString("login","")
                val extId = sharedPreferences.getString("externalId","")


                if ((startDate.dayOfMonth == day && startDate.monthValue == month && startDate.year == year) || (endDate.dayOfMonth == day && endDate.monthValue == month && endDate.year == year)){
                    for (i in 0 until eHour*4+eMinute-sHour*4-sMinute) {
                        var n = sHour*4+sMinute
                        if(book.user != "$extId ($username)") {
                            if(i==0){ //First row out of reservation
                                var startTime = ToLocalDateTime(book.startTime)
                                var endTime = ToLocalDateTime(book.endTime)

                                var startHour = startTime.hour
                                var startMinutes = startTime.minute
                                var endHour = endTime.hour
                                var endMinutes = endTime.minute

                                var hours=""

                                if(startMinutes<10 && endMinutes<10)
                                    hours = "$startHour:0$startMinutes-$endHour:0$endMinutes"
                                else if(startMinutes<10)
                                    hours = "$startHour:0$startMinutes-$endHour:$endMinutes"
                                else if(endMinutes<10)
                                    hours = "$startHour:$startMinutes-$endHour:0$endMinutes"
                                else hours = "$startHour:$startMinutes-$endHour:$endMinutes"

                                if(rowList[n+i].text.isEmpty()) {  //Name the reservation
                                    rowList[n + i].text = "${book.name}, $only_user, $hours"
                                    rowList[n + i].gravity = Gravity.CENTER
                                }
                                else {
                                    rowList[n + i + 1].text = "${book.name}, $only_user, $hours"
                                    rowList[n + i + 1].gravity = Gravity.CENTER
                                }
                                changeColorRedBorderTop(rowList[n + i])
                                BookedHoursOthers.add(rowList[n + i])
                            }
                            else if(i==eHour*4+eMinute-sHour*4-sMinute-1){ //Last row out of reservation
                                changeColorRedBorderBot(rowList[n + i])
                                BookedHoursOthers.add(rowList[n + i])
                            }
                            else { //Any other row
                                changeColorRed(rowList[n + i])
                                BookedHoursOthers.add(rowList[n + i])
                            }
                        }
                        else {
                            if(i==0){ //First row out of reservation
                                var startTime = ToLocalDateTime(book.startTime)
                                var endTime = ToLocalDateTime(book.endTime)

                                var startHour = startTime.hour
                                var startMinutes = startTime.minute
                                var endHour = endTime.hour
                                var endMinutes = endTime.minute

                                var hours=""

                                if(startMinutes<10 && endMinutes<10)
                                    hours = "$startHour:0$startMinutes-$endHour:0$endMinutes"
                                else if(startMinutes<10)
                                    hours = "$startHour:0$startMinutes-$endHour:$endMinutes"
                                else if(endMinutes<10)
                                    hours = "$startHour:$startMinutes-$endHour:0$endMinutes"
                                else hours = "$startHour:$startMinutes-$endHour:$endMinutes"

                                if(rowList[n+i].text.isEmpty()) {  //Name the reservation
                                    rowList[n + i].text = "${book.name}, $only_user, $hours"
                                    rowList[n + i].gravity = Gravity.CENTER
                                }
                                else {
                                    rowList[n + i + 1].text = "${book.name}, $only_user, $hours"
                                    rowList[n + i + 1].gravity = Gravity.CENTER
                                }
                                changeColorGreenBorderTop(rowList[n + i])
                                BookedHoursMy.add(rowList[n + i])
                            }
                            else if(i==eHour*4+eMinute-sHour*4-sMinute-1){
                                changeColorGreenBorderBot(rowList[n + i])
                                BookedHoursMy.add(rowList[n + i])
                            }
                            else {
                                changeColorGreen(rowList[n + i])
                                BookedHoursMy.add(rowList[n + i])
                            }
                        }
                    }
                }
            }
            else {
                var rEndDate = ToLocalDateTime(book.repeatEndTime)
                var createDate = ToLocalDateTime(book.creationTime)
                var chosenDate = LocalDateTime.of(year, month, day, createDate.hour, createDate.minute, createDate.second, createDate.nano )
                if (chosenDate.isBefore(rEndDate)){

                    if (book.repeatIntervalUnit == "d") {
                        while (createDate.isBefore(chosenDate)) {
                            createDate = createDate.plusDays(book.repeatInterval.toLong())
                        }
                    }
                    else if (book.repeatIntervalUnit == "m") {
                        while (createDate.isBefore(chosenDate)) {
                            createDate = createDate.plusMonths(book.repeatInterval.toLong())
                        }
                    }
                    else if (book.repeatIntervalUnit == "w") {
                        while (createDate.isBefore(chosenDate))
                            createDate = createDate.plusWeeks(book.repeatInterval.toLong())
                    }

                    val sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
                    val username = sharedPreferences.getString("login","")
                    val extId = sharedPreferences.getString("externalId","")

                    if (createDate.isEqual(chosenDate)){
                        for (i in 0..eHour*4+eMinute-sHour*4-sMinute-1) {
                            var n = sHour*4
                            if(book.user != "$extId ($username)") {
                                if(i==0){ //First row out of reservation
                                    var startTime = ToLocalDateTime(book.startTime)
                                    var endTime = ToLocalDateTime(book.endTime)

                                    var startHour = startTime.hour
                                    var startMinutes = startTime.minute
                                    var endHour = endTime.hour
                                    var endMinutes = endTime.minute

                                    var hours=""

                                    if(startMinutes<10 && endMinutes<10)
                                        hours = "$startHour:0$startMinutes-$endHour:0$endMinutes"
                                    else if(startMinutes<10)
                                        hours = "$startHour:0$startMinutes-$endHour:$endMinutes"
                                    else if(endMinutes<10)
                                        hours = "$startHour:$startMinutes-$endHour:0$endMinutes"
                                    else hours = "$startHour:$startMinutes-$endHour:$endMinutes"

                                    if(rowList[n+i].text.isEmpty()) {  //Name the reservation
                                        rowList[n + i].text = "${book.name}, $only_user, $hours"
                                        rowList[n + i].gravity = Gravity.CENTER
                                    }
                                    else {
                                        rowList[n + i + 1].text = "${book.name}, $only_user, $hours"
                                        rowList[n + i + 1].gravity = Gravity.CENTER
                                    }
                                    changeColorRedBorderTop(rowList[n + i])
                                    BookedHoursOthers.add(rowList[n + i])
                                }
                                else if(i==eHour*4+eMinute-sHour*4-sMinute-1){
                                    changeColorRedBorderBot(rowList[n + i])
                                    BookedHoursOthers.add(rowList[n + i])
                                }
                                else {
                                    changeColorRed(rowList[n + i])
                                    BookedHoursOthers.add(rowList[n + i])
                                }
                            }
                            else {
                                if(i==0){ //First row out of reservation
                                    var startTime = ToLocalDateTime(book.startTime)
                                    var endTime = ToLocalDateTime(book.endTime)

                                    var startHour = startTime.hour
                                    var startMinutes = startTime.minute
                                    var endHour = endTime.hour
                                    var endMinutes = endTime.minute

                                    var hours=""

                                    if(startMinutes<10 && endMinutes<10)
                                        hours = "$startHour:0$startMinutes-$endHour:0$endMinutes"
                                    else if(startMinutes<10)
                                        hours = "$startHour:0$startMinutes-$endHour:$endMinutes"
                                    else if(endMinutes<10)
                                        hours = "$startHour:$startMinutes-$endHour:0$endMinutes"
                                    else hours = "$startHour:$startMinutes-$endHour:$endMinutes"

                                    if(rowList[n+i].text.isEmpty()) {  //Name the reservation
                                        rowList[n + i].text = "${book.name}, $only_user, $hours"
                                        rowList[n + i].gravity = Gravity.CENTER
                                    }
                                    else {
                                        rowList[n + i + 1].text = "${book.name}, $only_user, $hours"
                                        rowList[n + i + 1].gravity = Gravity.CENTER
                                    }

                                    changeColorGreenBorderTop(rowList[n + i])
                                    BookedHoursMy.add(rowList[n + i])
                                }
                                else if(i==eHour*4+eMinute-sHour*4-sMinute-1){
                                    changeColorGreenBorderBot(rowList[n + i])
                                    BookedHoursMy.add(rowList[n + i])
                                }
                                else {
                                    changeColorGreen(rowList[n + i])
                                    BookedHoursMy.add(rowList[n + i])
                                }
                            }

                        }
                    }
                }

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun makeReservation(v: View){
        val txtV = v as TextView
        for(booked in BookedHoursOthers){
            if(v == booked){
                Toast.makeText(this, resources.getString(R.string.already_taken), Toast.LENGTH_SHORT).show()
                return
            }
        }

        for(booked in BookedHoursMy){
            if(v == booked){
                showSimpleDialog(v)
                return
            }
        }

        var hour = 0
        var minutes = 0
        for (i in 0..rowList.size-1){
            if(v == rowList[i]){
                hour = i/4
                minutes = i%4 * 15
            }
        }
        val intent = Intent(this, MakeReservationActivity::class.java)
        intent.putExtra("hour", hour)
        intent.putExtra("minute", minutes)
        intent.putExtra("day", mday)
        intent.putExtra("month", mmonth)
        intent.putExtra("year", myear)
        intent.putExtra("item", item as Serializable)
        startActivity(intent)
    }


    private fun changeColorRed(tv: TextView){
        tv.background = resources.getDrawable(R.drawable.border_red)
    }
    private fun changeColorGreen(tv: TextView){
        tv.background = resources.getDrawable(R.drawable.border_green)
    }
    private fun changeColorRedBorderTop(tv: TextView){
        tv.background = resources.getDrawable(R.drawable.border_red_top)
    }
    private fun changeColorRedBorderBot(tv: TextView){
        tv.background = resources.getDrawable(R.drawable.border_red_bot)
    }
    private fun changeColorGreenBorderTop(tv: TextView){
        tv.background = resources.getDrawable(R.drawable.border_green_top)
    }
    private fun changeColorGreenBorderBot(tv: TextView){
        tv.background = resources.getDrawable(R.drawable.border_green_bot)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showSimpleDialog(view: View){
        var hour = 0
        var minutes = 0
        var startHour = 0
        var startMinutes = 0
        var endHour = 0
        var endMinutes = 0
        for (i in 0..rowList.size-1){
            if(view == rowList[i]){
                hour = i/4
                minutes = i%4 * 15
            }
        }
        for (book in item.bookings){
            var current = hour*60 + minutes
            var startTime = ToLocalDateTime(book.startTime)
            var endTime = ToLocalDateTime(book.endTime)

            if(startTime.dayOfMonth==mday&&startTime.monthValue==mmonth&&startTime.year==myear) {

                var start = startTime.hour * 60 + startTime.minute
                var end = endTime.hour * 60 + endTime.minute

                if (current >= start - 14 && current <= end - 14) {

                    Log.d("Success", startTime.toString())
                    Log.d("Success", endTime.toString())
                    startHour = startTime.hour
                    startMinutes = startTime.minute
                    endHour = endTime.hour
                    endMinutes = endTime.minute
                }
            }
        }
        var hours: String
        if(startMinutes<10 && endMinutes<10)
            hours = "$startHour:0$startMinutes-$endHour:0$endMinutes"
        else if(startMinutes<10)
            hours = "$startHour:0$startMinutes-$endHour:$endMinutes"
        else if(endMinutes<10)
            hours = "$startHour:$startMinutes-$endHour:0$endMinutes"
        else hours = "$startHour:$startMinutes-$endHour:$endMinutes"

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete that reservation? ($hours)")

        builder.setPositiveButton("Yes") { dialog, _ ->
            dialog.dismiss()
            deleteReservation(view)
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog:AlertDialog = builder.create()
        alertDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun deleteReservation(v:View){
        val txtV = v as TextView

        var hour = 0
        var minutes = 0
        var startTime:LocalDateTime = LocalDateTime.now()
        var endTime: LocalDateTime
        var bookingToDelete = Booking()

        for (i in 0..rowList.size-1){
            if(v == rowList[i]){
                hour = i/4
                minutes = i%4 * 15
            }
        }

        for (book in item.bookings) {
            var current = hour * 60 + minutes
            startTime = ToLocalDateTime(book.startTime)
            endTime = ToLocalDateTime(book.endTime)

            if (startTime.dayOfMonth == mday && startTime.monthValue == mmonth && startTime.year == myear) {

                var start = startTime.hour * 60 + startTime.minute
                var end = endTime.hour * 60 + endTime.minute

                if (current >= start - 14 && current <= end - 14) {
                    bookingToDelete = book
                }
            }
        }

        if (Build.VERSION.SDK_INT > 9) { //Delete Booking
            val policy =
                StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        val sharedPreferencesUser = getSharedPreferences("User", Context.MODE_PRIVATE)
        var sessionId = sharedPreferencesUser.getString("sessionId","").toString()

        val sharedPreferencesCategory = getSharedPreferences("Category", Context.MODE_PRIVATE)
        var categoryId = sharedPreferencesCategory.getString("Id", "")

        val (_, _, result) = Fuel.delete("http://51.178.82.249:2067/api/user/category/$categoryId/item/${item.id}/booking/${bookingToDelete.id}")
            .header("session-id", sessionId)
            .responseString()

        val (data, _) = result


        result.fold(success = { json ->

            var gson = Gson()
            var Response = gson.fromJson(data, ResponseJson.ResponseInfo::class.java)

            Log.d("Success", json)

            when (Response.code){
                "notLogged" -> {
                    Logout()
                    Toast.makeText(this, resources.getString(R.string.not_logged), Toast.LENGTH_LONG).show()
                }
                "correct" -> {
                    Toast.makeText(this, resources.getString(R.string.correct), Toast.LENGTH_LONG).show()
                    //go to Reservation
                    val intent = Intent(this, ReservationActivity::class.java)
                    intent.putExtra("day", startTime.dayOfMonth)
                    intent.putExtra("month", startTime.monthValue)
                    intent.putExtra("year", startTime.year)
                    intent.putExtra("item", item as Serializable)
                    startActivity(intent)
                }
                "entityNotExists" -> Toast.makeText(this, resources.getString(R.string.entity_not_exists), Toast.LENGTH_LONG).show()
                "missingPermissions" -> Toast.makeText(this, resources.getString(R.string.missing_permissions), Toast.LENGTH_LONG).show()
                "masterAdminNotAllowed" -> Toast.makeText(this, resources.getString(R.string.master_admin_not_allowed), Toast.LENGTH_LONG).show()
            }

        }, failure = { error ->
            Log.e("Failure", error.toString())
        })


    }

    fun updateReservations(){
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


        result.fold(success = { _ ->

            var gson = Gson()
            var CategoryJson = gson.fromJson(data, CategoryJson.CategoryInfo::class.java)

            when (CategoryJson.code){
                "notLogged" -> {
                    Logout()
                    Toast.makeText(this, resources.getString(R.string.not_logged), Toast.LENGTH_LONG).show()
                }
                "correct" -> {
                    var items = ArrayList<Item>()

                    for (i in CategoryJson.data.items) {
                        var nItem = Item()
                        nItem.id = i.id
                        nItem.description = i.description
                        nItem.name = i.name
                        nItem.attributes = i.attributes
                        nItem.imagesBase64 = i.imagesBase64
                        nItem.categoryId = i.categoryId
                        nItem.bookingAuthorizationRequired = i.bookingAuthorizationRequired
                        nItem.responsibleUsers = i.responsibleUsers
                        nItem.bookings = i.bookings

                        if (item.id==nItem.id){
                            item.bookings = nItem.bookings
                        }

                        items.add(nItem)
                    }

                }
                "missingFields" -> Toast.makeText(this, resources.getString(R.string.missing_fields), Toast.LENGTH_LONG).show()
                "invalidData" -> Toast.makeText(this, resources.getString(R.string.invalid_data), Toast.LENGTH_LONG).show()
                "entityAlreadyExists" -> Toast.makeText(this, resources.getString(R.string.entity_already_exists), Toast.LENGTH_LONG).show()
                "entityNotExists" -> Toast.makeText(this, resources.getString(R.string.entity_not_exists), Toast.LENGTH_LONG).show()
                "masterAdminNotAllowed" -> Toast.makeText(this, resources.getString(R.string.master_admin_not_allowed), Toast.LENGTH_LONG).show()

            }

        }, failure = { error ->
            Log.e("Failure", error.toString())
        })


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun ToLocalDateTime(date: Date): LocalDateTime{

            return date.time.let {
                Instant.ofEpochMilli(it)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()

        };
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
