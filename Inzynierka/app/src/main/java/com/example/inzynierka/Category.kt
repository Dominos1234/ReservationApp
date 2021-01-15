package com.example.inzynierka

class Category {
    var id: String = ""
    var name: String = ""
    var description: String = ""
    var imageBase64: String = ""
    var attributes: List<AttributeName>? = null
    var items: List<Item>? = null
}