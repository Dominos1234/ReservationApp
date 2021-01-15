package com.example.inzynierka


class CategoriesJson {

    data class CategoryInfo(
        val code: String,
        val data: List<Data>
    )

    data class Data(
        val id: String,
        val name: String,
        val description: String,
        val imageBase64: String,
        val attributes: List<AttributeName>
    )
}