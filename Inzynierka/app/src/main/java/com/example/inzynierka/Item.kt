package com.example.inzynierka

class Item {
    var id: String = ""
    var categoryId = ""
    var name: String = ""
    var description: String = ""
    var imagesBase64: List<String>? = null
    var attributes: List<AttributeValue>? = null

    var bookingAuthorizationRequired: Boolean = false
    var responsibleUsers: List<String>? = null
    var bookings: List<Booking>? = null
}