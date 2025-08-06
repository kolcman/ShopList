package com.example.shoppinglist.data

import com.example.shoppinglist.data.db.ShopItemDao
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

class ShopListRepositoryImpl(private val shopItemDao: ShopItemDao) :
    ShopListRepository {

    override suspend fun addShopItem(item: ShopItem) {
        shopItemDao.addShopItem(item)
    }

    override suspend fun removeShopItem(itemId: Int) {
        shopItemDao.deleteShopItem(itemId)
    }

    override suspend fun editShopItem(item: ShopItem) {
        shopItemDao.editShopItem(item)
    }

    override suspend fun getItemFromId(shopItemId: Int): ShopItem {
        return shopItemDao.getShopItem(shopItemId)
    }

    override suspend fun getShopList(): List<ShopItem> {
        return shopItemDao.getAll()
    }

}
