package com.example.shoppinglist.presentation.ui.shopItem

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var listItems = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_shop_enabled,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ShopItemViewHolder,
        position: Int
    ) {
        val item = listItems[position]
        val status = if (item.isActive) "Active" else "Inactive"
        holder.tvTitle?.text = "${item.name} $status"
        holder.tvCount?.text = item.count.toString()
    }

    override fun getItemCount(): Int {
        return  listItems.size
    }


    class ShopItemViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView? = itemView.findViewById(R.id.tv_item_title)
        val tvCount: TextView? = itemView.findViewById(R.id.tv_item_count)

        val view = itemView

        init {
            view.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    Log.d("TAG","Click on $position")
                    true
                } else {
                    false
                }
            }
        }
    }
}