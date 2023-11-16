package com.example.shoplist.data

import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.ShopListRepository

object ShopListRepositoryImpl: ShopListRepository  {

    private val shopList = mutableListOf<ShopItem>()

    private var autoIncrementId = 0

    init {
        for (i in 0 until 10){
            val item = ShopItem("Name $i", i ,true)
            addShopItem(item)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID)
        shopList.add(shopItem)
    }

    override fun removeShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find{
            it.id == shopItemId
        } ?: throw  RuntimeException("This id $shopItemId, not found")
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }
}