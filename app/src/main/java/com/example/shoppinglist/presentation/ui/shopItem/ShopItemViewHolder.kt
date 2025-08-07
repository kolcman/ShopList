package com.example.shoppinglist.presentation.ui.shopItem

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class ShopItemViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvTitle: TextView? = itemView.findViewById(R.id.tv_item_title)
    val tvCount: TextView? = itemView.findViewById(R.id.tv_item_count)
}