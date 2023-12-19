package com.capstone.terratani.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.terratani.R
import com.capstone.terratani.model.ImageShop

class ShopRecyclerView(private val imageShopList: List<ImageShop>): RecyclerView.Adapter<ShopRecyclerView.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shop_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageShopList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentImageShop = imageShopList[position]

        holder.itemView.findViewById<ImageView>(R.id.iv_image_shop).setImageResource(currentImageShop.image)
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}