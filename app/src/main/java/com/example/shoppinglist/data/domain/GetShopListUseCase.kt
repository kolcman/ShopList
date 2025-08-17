package com.example.shoppinglist.data.domain

import androidx.lifecycle.LiveData
import com.example.shoppinglist.data.db.ShopItemDbModel

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopList(): LiveData<List<ShopItem>> {
        return shopListRepository.getShopList()
    }
}