package com.example.shoppinglist.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.databinding.FragmentShopItemBinding
import com.example.shoppinglist.presentation.viewModel.ShopItemViewModel

class ShopItemFragment : Fragment() {

    private var _binding: FragmentShopItemBinding? = null
    val binding: FragmentShopItemBinding
        get() = _binding ?: throw IllegalStateException("Binding == null")

    private lateinit var onEditingFinishedListeners: OnEditingFinishedListeners
    private var screenMode = MODE_UNKNOWN
    private var shopItemId = UNDEFINED_ID
    private lateinit var viewModel: ShopItemViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListeners){
            onEditingFinishedListeners = context
        } else  {
            throw RuntimeException("Activity must implement onEditingFinishedListeners")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShopItemBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        setUpWatchers()
        launchRightMode()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.shopItem.observe(viewLifecycleOwner) { item ->
            binding.etName.setText(item.name)
            binding.etCount.setText(item.count.toString())
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListeners.onEditingFinished()
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        binding.btnSave.setOnClickListener {
            viewModel.updateItem(binding.etName.text?.toString(), binding.etCount.text?.toString())
        }
    }

    private fun launchAddMode() {
       binding.btnSave.setOnClickListener {
            viewModel.saveItem(binding.etName.text?.toString(), binding.etCount.text?.toString())
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID, UNDEFINED_ID)
        }
    }

    private fun setUpWatchers() {
        binding.etName.doOnTextChanged { text, _, _, _ ->
            viewModel.resetInputName()
        }
        binding.etCount.doOnTextChanged { text, _, _, _ ->
            viewModel.resetInputCount()
        }
    }

    interface OnEditingFinishedListeners{
        fun onEditingFinished()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val UNDEFINED_ID = -1
        private const val MODE_UNKNOWN = ""
        private const val SCREEN_MODE = "extra_mode"
        private const val SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "extra_edit"
        private const val MODE_ADD = "extra_add"


        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }
}
