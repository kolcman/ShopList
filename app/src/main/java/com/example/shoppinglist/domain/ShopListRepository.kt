package com.example.shoppinglist.domain

interface ShopListRepository {

    fun addShopItem(item: ShopItem)

    fun removeShopItem(item: ShopItem)

    fun editShopItem(item: ShopItem)

    fun getItemFromId(shopItemId: Int): ShopItem

    fun getShopList(): List<ShopItem>
}