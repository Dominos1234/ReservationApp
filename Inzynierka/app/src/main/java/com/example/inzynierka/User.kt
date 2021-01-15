package com.example.inzynierka

class User {

    data class UserInfo(
        val code: String,
        val data: Data
    )

    data class Data(
        val id: String,
        val login: String,
        val password: String,
        val externalId: String,
        val adminUser: Boolean
    )
}