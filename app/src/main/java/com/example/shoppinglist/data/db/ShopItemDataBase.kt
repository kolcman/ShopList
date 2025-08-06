package com.example.shoppinglist.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shoppinglist.domain.ShopItem

@Database(entities = [ShopItem::class], version = 1)
abstract class ShopItemDataBase: RoomDatabase() {
    abstract fun shopItemDao(): ShopItemDao
}