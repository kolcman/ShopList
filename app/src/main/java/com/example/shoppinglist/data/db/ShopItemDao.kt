package com.example.shoppinglist.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinglist.domain.ShopItem

@Dao
interface ShopItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(shopItem: ShopItem)

    @Query("DELETE FROM  shopItems WHERE id = :shopItemId")
    suspend fun deleteShopItem(shopItemId: Int)

    @Query("SELECT * FROM shopItems")
    fun getAll(): LiveData<List<ShopItem>>

    @Query("SELECT * FROM shopItems WHERE id = :shopItemId")
    suspend fun getShopItem(shopItemId:Int): ShopItem

    @Update
    suspend fun editShopItem(shopItem: ShopItem)
}