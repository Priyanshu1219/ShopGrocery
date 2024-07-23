package com.example.loginapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GroceryAdapter(private val originalItems: ArrayList<GroceryItem>) : RecyclerView.Adapter<GroceryAdapter.ViewHolder>() {
    private var items: ArrayList<GroceryItem> = ArrayList(originalItems)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemName: TextView = itemView.findViewById(R.id.name)
        val itemPrice: TextView = itemView.findViewById(R.id.price)
        val itemImage: ImageView = itemView.findViewById(R.id.image)
        val quantityTextView: TextView = itemView.findViewById(R.id.itemCount)
        val increaseButton: ImageButton = itemView.findViewById(R.id.plus)
        val decreaseButton: ImageButton = itemView.findViewById(R.id.minus)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemName.text = item.name
        holder.itemPrice.text = item.price
        Glide.with(holder.itemView.context)
            .load(item.image)
            .into(holder.itemImage)
        holder.quantityTextView.text = item.quantity.toString()

        holder.increaseButton.setOnClickListener {
            item.quantity++
            holder.quantityTextView.text = item.quantity.toString()
        }

        holder.decreaseButton.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity--
                holder.quantityTextView.text = item.quantity.toString()
            }
        }
    }
    fun filterList(filteredItems: ArrayList<GroceryItem>) {
        items.clear()
        items.addAll(filteredItems)
        notifyDataSetChanged()
    }
}