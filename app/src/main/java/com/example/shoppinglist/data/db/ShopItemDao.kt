package com.example.shoppinglist.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinglist.domain.ShopItem

@Dao
interface ShopItemDao {

    @Insert
    fun addShopItem(shopItem: ShopItem)

    @Query("DELETE FROM  shopItems WHERE id = :shopItemId")
    fun deleteShopItem(shopItemId: Int)

    @Query("SELECT * FROM shopItems")
    fun getAll(): List<ShopItem>

    @Query("SELECT * FROM shopItems WHERE id = :shopItemId")
    fun getShopItem(shopItemId:Int): ShopItem

    @Update
    fun editShopItem(shopItem: ShopItem)

}