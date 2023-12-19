package com.capstone.terratani.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.terratani.R
import com.capstone.terratani.model.Features
import com.capstone.terratani.ui.detail.DetailFeaturesActivity
import com.capstone.terratani.ui.detection.DetectionActivity

class FeaturesRecyclerView(private val featuresList: List<Features>): RecyclerView.Adapter<FeaturesRecyclerView.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.features_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return featuresList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentFeatures = featuresList[position]

        holder.itemView.findViewById<TextView>(R.id.tv_features).text = currentFeatures.title

        // Navigate to Detail
        holder.itemView.findViewById<CardView>(R.id.card_features).setOnClickListener {
            if (position == 0) {
                val intent = Intent(holder.itemView.context, DetectionActivity::class.java)
                holder.itemView.context.startActivity(intent)
            } else {
                val intent = Intent(holder.itemView.context, DetailFeaturesActivity::class.java)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}