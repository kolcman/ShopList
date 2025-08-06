package com.example.shoppinglist.domain

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {

   suspend fun getItemFromId(shopItemId: Int): ShopItem {
       return shopListRepository.getItemFromId(shopItemId)
    }
}