package com.example.shoppinglist.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ShopItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(shopItem: ShopItemDbModel)

    @Query("DELETE FROM  shopItems WHERE id = :shopItemId")
    suspend fun deleteShopItem(shopItemId: Int)

    @Query("SELECT * FROM shopItems")
    fun getAll(): LiveData<List<ShopItemDbModel>>

    @Query("SELECT * FROM shopItems WHERE id = :shopItemId LIMIT 1")
    suspend fun getShopItem(shopItemId:Int): ShopItemDbModel

    @Update
    suspend fun editShopItem(shopItem: ShopItemDbModel)
}