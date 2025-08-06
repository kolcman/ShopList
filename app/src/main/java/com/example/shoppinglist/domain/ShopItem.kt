package com.example.shoppinglist.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopItems")
data class ShopItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val count: Int,
    val isActive: Boolean
)

