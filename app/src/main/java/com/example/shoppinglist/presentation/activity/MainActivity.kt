package com.example.shoppinglist.presentation.activity

import android.app.FragmentContainer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.presentation.fragment.ShopItemFragment
import com.example.shoppinglist.presentation.ui.shopItem.ShopListAdapter
import com.example.shoppinglist.presentation.viewModel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListeners {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var touchHelper: ItemTouchHelper

    private lateinit var btnAdd: FloatingActionButton
    private val scope = CoroutineScope(Dispatchers.Main)
    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        shopItemContainer = findViewById(R.id.shop_item_container)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initViews()
        setUpRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopItems.observe(this) {
            shopListAdapter.submitList(it)
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(
            this,
            "Success",
            Toast.LENGTH_SHORT
        ).show()
        supportFragmentManager.popBackStack()
    }

    private fun isOnePaneMode(): Boolean {
        return shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setUpRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_item)
        with(rvShopList) {
            shopListAdapter = ShopListAdapter()
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
        setUpListeners()
        setUpSwipeListeners()
        touchHelper.attachToRecyclerView(rvShopList)
    }

    private fun setUpListeners() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.toggleShopItem(it.id)
        }
        shopListAdapter.onShopItemClickListener = {
            if (isOnePaneMode()) {
                startActivity(ShopItemActivity.newIntentEdit(this, it.id))
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }

        }
        btnAdd.setOnClickListener {
            if (isOnePaneMode()) {
                startActivity(ShopItemActivity.newIntentAdd(this))
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
    }

    private fun setUpSwipeListeners() {
        touchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.removeShopItem(item.id)
            }
        })
    }

    private fun initViews() {
        btnAdd = findViewById<FloatingActionButton>(R.id.btn_add_shop_item)
    }
}