package com.example.inzynierka

import java.util.*

class Booking {
    var id: String = ""
    var itemId = ""
    var categoryId: String = ""
    var userId: String = ""
    var name: String = ""
    var user: String = ""

    var startTime: Date? = null
    var endTime: Date? = null
    var creationTime: Date? = null

    var repeatInterval: String = ""
    var repeatIntervalUnit: String = ""
    var repeatEndTime: Date? = null

    var confirmed: Boolean = false
}