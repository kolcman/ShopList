package com.example.shoppinglist.data.domain

import com.example.shoppinglist.data.db.ShopItemDbModel

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun editShopItem(item: ShopItem) {
        shopListRepository.editShopItem(item)
    }
}