package com.example.inzynierka

import java.io.Serializable

class Item: Serializable {
    var id: String = ""
    var categoryId = ""
    var name: String = ""
    var description: String = ""
    var imagesBase64: List<String> = listOf<String>()
    var attributes: List<AttributeValue> = listOf<AttributeValue>()

    var bookingAuthorizationRequired: Boolean = false
    var responsibleUsers: List<String> = listOf<String>()
    var bookings: ArrayList<Booking> = arrayListOf<Booking>()
}