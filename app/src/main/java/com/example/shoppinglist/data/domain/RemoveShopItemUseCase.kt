package com.example.shoppinglist.data.domain

class RemoveShopItemUseCase(private val shopListRepository: ShopListRepository) {

   suspend fun removeShopItem(itemId: Int) {
        shopListRepository.removeShopItem(itemId)
    }
}