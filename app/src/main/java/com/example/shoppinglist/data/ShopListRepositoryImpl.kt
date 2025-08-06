package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import com.example.shoppinglist.data.db.ShopItemDao
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl :
    ShopListRepository {


    private lateinit var dao: ShopItemDao

    fun init(dao: ShopItemDao) {
        this.dao = dao
    }

    override suspend fun addShopItem(item: ShopItem) {
        dao.addShopItem(item)
    }

    override suspend fun removeShopItem(shopItemId: Int) {
        dao.deleteShopItem(shopItemId)
    }

    override suspend fun editShopItem(item: ShopItem) {
        dao.editShopItem(item)
    }

    override suspend fun getItemFromId(shopItemId: Int): ShopItem {
        return dao.getShopItem(shopItemId)
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return dao.getAll()
    }
}
