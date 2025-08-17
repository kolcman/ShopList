package com.example.shoppinglist.presentation.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.data.domain.EditShopItemUseCase
import com.example.shoppinglist.data.domain.GetShopItemUseCase
import com.example.shoppinglist.data.domain.GetShopListUseCase
import com.example.shoppinglist.data.domain.RemoveShopItemUseCase
import com.example.shoppinglist.data.domain.ShopItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopItem = GetShopItemUseCase(repository)
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopItems = getShopListUseCase.getShopList()

    fun removeShopItem(itemId: Int) {
        viewModelScope.launch {
            removeShopItemUseCase.removeShopItem(itemId)
        }
    }

    fun editShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            editShopItemUseCase.editShopItem(shopItem)
        }
    }

    fun toggleShopItem(id: Int) {
        viewModelScope.launch {
            Log.d("TOGGLE", "toggleShopItem called for id = $id")
            val item = getShopItem.getItemFromId(id)
            val updatedItem = item.copy(isActive = !item.isActive)
            editShopItem(updatedItem)
        }
    }

}