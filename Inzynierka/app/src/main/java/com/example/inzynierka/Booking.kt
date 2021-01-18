package com.example.inzynierka

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class Booking: Serializable {
    var id: String = ""
    var itemId = ""
    var categoryId: String = ""
    var userId: String = ""
    var name: String = ""
    var user: String = ""

    var startTime: Date = Date()
    var endTime: Date = Date()
    var creationTime: Date = Date()

    var repeatInterval: String = ""
    var repeatIntervalUnit: String = ""
    var repeatEndTime: Date = Date()

    var confirmed: Boolean = false
}