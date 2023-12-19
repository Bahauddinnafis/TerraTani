package com.capstone.terratani.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.terratani.R
import com.capstone.terratani.model.Plant

class PlantRecyclerView(private val plantList: List<Plant>): RecyclerView.Adapter<PlantRecyclerView.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plant_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPlant = plantList[position]

        holder.itemView.findViewById<TextView>(R.id.tv_title).text = currentPlant.name
        holder.itemView.findViewById<TextView>(R.id.tv_description).text = currentPlant.description
        holder.itemView.findViewById<ImageView>(R.id.iv_plant_image).setImageResource(currentPlant.photo)
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

}