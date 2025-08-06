package com.example.shoppinglist.domain

class RemoveShopItemUseCase(private val shopListRepository: ShopListRepository) {

   suspend fun removeShopItem(itemId: Int) {
        shopListRepository.removeShopItem(itemId)
    }
}