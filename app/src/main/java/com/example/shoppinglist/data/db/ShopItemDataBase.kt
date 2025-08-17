package com.example.shoppinglist.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDbModel::class], version = 1)
abstract class ShopItemDataBase : RoomDatabase() {
    abstract fun shopItemDao(): ShopItemDao

    companion object {

        private const val DB_NAME = "shop_item.dp"
        private val lock = Any()

        @Volatile
        private var INSTANCE: ShopItemDataBase? = null

        fun getInstance(application: Application): ShopItemDataBase {
            INSTANCE?.let {
                return it
            }
           synchronized(lock) {
                INSTANCE?.let { return it }
                val instance = Room.databaseBuilder(
                    application,

                    ShopItemDataBase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}