package com.example.inzynierka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.TextView
import java.io.Serializable

class CalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val item: Item = intent.getSerializableExtra("item") as Item

        val calendar = findViewById<CalendarView>(com.example.inzynierka.R.id.calendar)

        calendar.setOnDateChangeListener {calendar, year, month, day ->
            val intent = Intent(this, ReservationActivity::class.java)
            intent.putExtra("day", day)
            intent.putExtra("month", month+1)
            intent.putExtra("year", year)
            intent.putExtra("item", item as Serializable)

            startActivity(intent)
        }
    }
}
