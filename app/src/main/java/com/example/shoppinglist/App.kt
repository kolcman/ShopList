package com.example.shoppinglist

import android.app.Application
import androidx.room.Room
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.data.db.ShopItemDataBase
import com.example.shoppinglist.domain.ShopListRepository

class App : Application() {
    private val dataBase: ShopItemDataBase by lazy {
        Room.databaseBuilder(
            applicationContext,
            ShopItemDataBase::class.java, "app-database.db"
        ).build()
    }

    override fun onCreate() {
        super.onCreate()
        ShopListRepositoryImpl.init(dataBase.shopItemDao())
    }
}



