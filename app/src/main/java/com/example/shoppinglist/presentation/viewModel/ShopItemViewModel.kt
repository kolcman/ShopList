package com.example.shoppinglist.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.data.db.ShopItemDbModel
import com.example.shoppinglist.data.domain.AddShopItemUseCase
import com.example.shoppinglist.data.domain.EditShopItemUseCase
import com.example.shoppinglist.data.domain.GetShopItemUseCase
import com.example.shoppinglist.data.domain.ShopItem
import kotlinx.coroutines.launch

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {

    private val _errorInputName = MutableLiveData<Boolean>(false)
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName
    private val _errorInputCount = MutableLiveData<Boolean>(false)
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    private val repository = ShopListRepositoryImpl(application)
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val saveItemUseCase = AddShopItemUseCase(repository)
    private val editItemUseCase = EditShopItemUseCase(repository)

    fun getShopItem(shopItemId: Int) {
        viewModelScope.launch {
            val item = getShopItemUseCase.getItemFromId(shopItemId)
            _shopItem.value = item
        }
    }

    fun saveItem(name: String?, count: String?) {
        val name = parseName(name)
        val count = parseCount(count)
        if (validateInput(name, count)) {
            viewModelScope.launch {
                saveItemUseCase.addShopItem(ShopItem(name = name, count = count))
                finishWork()
            }
        }
    }

    fun updateItem(name: String?, count: String?) {
        val name = parseName(name)
        val count = parseCount(count)
        if (validateInput(name, count)) {
            viewModelScope.launch {
                _shopItem.value?.let {
                    val item = it.copy(name = name, count = count)
                    editItemUseCase.editShopItem(item)
                    finishWork()
                }
            }
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: NumberFormatException) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }

    fun resetInputName() {
        _errorInputName.value = false
    }

    fun resetInputCount() {
        _errorInputCount.value = false
    }
}