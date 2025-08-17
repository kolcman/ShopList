package com.example.shoppinglist.data.domain

data class ShopItem(
    val id: Int = 0,
    val name: String,
    val count: Int,
    val isActive: Boolean = true
)