package com.example.shoppinglist.data.domain

import com.example.shoppinglist.data.db.ShopItemDbModel

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {

   suspend fun getItemFromId(shopItemId: Int): ShopItem {
       return shopListRepository.getItemFromId(shopItemId)
    }
}