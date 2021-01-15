package com.example.inzynierka

class ItemJson {
    data class ItemInfo(
        val code: String,
        val data: Data
    )

    data class Data(
        val id: String,
        val name: String,
        val categoryId: String,
        val description: String,
        val imageBase64: String,
        val attributes: List<AttributeValue>,
        val bookingAuthorizationRequired: Boolean,
        val responsibleUsers: List<String>,
        val bookings: List<Booking>
    )
}