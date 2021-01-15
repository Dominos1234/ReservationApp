package com.example.inzynierka

class CategoryJson {
    data class CategoryInfo(
        val code: String,
        val data: Data
    )

    data class Data(
        val id: String,
        val name: String,
        val description: String,
        val imageBase64: String,
        val attributes: List<AttributeName>,
        val items: List<Item>
    )
}