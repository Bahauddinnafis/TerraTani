package com.capstone.terratani.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.terratani.R
import com.capstone.terratani.model.Category
import com.capstone.terratani.ui.detail.DetailActivity

class CategoryRecyclerView(private val categoryList: List<Category>): RecyclerView.Adapter<CategoryRecyclerView.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentCategory = categoryList[position]

        holder.itemView.findViewById<ImageView>(R.id.iv_foto_category).setImageResource(currentCategory.photo)
        holder.itemView.findViewById<TextView>(R.id.tv_title_category).text = currentCategory.title
        holder.itemView.findViewById<TextView>(R.id.tv_category_price).text = currentCategory.price
        holder.itemView.findViewById<TextView>(R.id.tv_city).text = currentCategory.city

        // Intent to Detail Activity
        holder.itemView.findViewById<CardView>(R.id.card_category).setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}