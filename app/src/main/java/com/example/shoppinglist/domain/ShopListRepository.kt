package com.example.shoppinglist.domain

interface ShopListRepository {

    suspend fun addShopItem(item: ShopItem)

    suspend fun removeShopItem(item: Int)

    suspend fun editShopItem(item: ShopItem)

    suspend fun getItemFromId(shopItemId: Int): ShopItem

    suspend fun getShopList(): List<ShopItem>
}