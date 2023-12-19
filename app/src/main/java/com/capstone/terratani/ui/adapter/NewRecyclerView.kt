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

class NewRecyclerView(private val newList: List<Category>): RecyclerView.Adapter<NewRecyclerView.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.new_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewRecyclerView.MyViewHolder, position: Int) {
        val currentNew = newList[position]

        holder.itemView.findViewById<TextView>(R.id.tv_title_new).text = currentNew.title
        holder.itemView.findViewById<TextView>(R.id.tv_new_price).text = currentNew.price
        holder.itemView.findViewById<TextView>(R.id.tv_city).text = currentNew.city
        holder.itemView.findViewById<ImageView>(R.id.iv_foto_new).setImageResource(currentNew.photo)

        // Intent to Detail Activity
        holder.itemView.findViewById<CardView>(R.id.card_new).setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return newList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}