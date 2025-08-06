package com.example.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val scope = CoroutineScope(Dispatchers.Default)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopItems.observe(this) {
            Log.d("Main", it.toString())
        }
        scope.launch {
            viewModel.getShopList()
            viewModel.addShopItem(ShopItem(1,"Test1", 1, true))
            viewModel.addShopItem(ShopItem(2,"Test1", 4, false))
            viewModel.addShopItem(ShopItem(3,"Test1", 8, true))
            viewModel.removeShopItem(2)
            viewModel.editShopItem(ShopItem(1,"Test1", 1, false))
            viewModel.editShopItem(ShopItem(3,"Test1", 7, false))
        }


    }
}