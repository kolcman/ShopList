package com.example.shoppinglist.domain

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun getItemFromId(shopItemId: Int): ShopItem {
       return shopListRepository.getItemFromId(shopItemId)
    }
}