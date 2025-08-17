package com.example.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.shoppinglist.data.db.ShopItemDataBase
import com.example.shoppinglist.data.domain.ShopItem
import com.example.shoppinglist.data.domain.ShopListRepository


class ShopListRepositoryImpl(application: Application) :
    ShopListRepository {

    private val dao = ShopItemDataBase.getInstance(application).shopItemDao()
    private val mapper = ShopListMapper()

    override suspend fun addShopItem(item: ShopItem) {
        dao.addShopItem(mapper.mapEntityToDbModel(item))
    }

    override suspend fun removeShopItem(shopItemId: Int) {
        dao.deleteShopItem(shopItemId)
    }

    override suspend fun editShopItem(item: ShopItem) {
        dao.editShopItem(mapper.mapEntityToDbModel(item))
    }

    override suspend fun getItemFromId(shopItemId: Int): ShopItem {
        val dbModel = dao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return dao.getAll().map {  mapper.mapListDbModelToListEntity(it) }
    }
}
