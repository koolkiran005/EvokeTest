package com.kiran.evoketest.android.model

data class User(
    val id: Int,
    val name: String?,
    val username: String?,
    val email: String?,
    val phone: String?,
    val address: Address?,
    val website: String?,
    val company: Company?
)