package com.example.shoppinglist.data.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    suspend fun addShopItem(item: ShopItem)

    suspend fun removeShopItem(shopItemId: Int)

    suspend fun editShopItem(item: ShopItem)

    suspend fun getItemFromId(shopItemId: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>
}