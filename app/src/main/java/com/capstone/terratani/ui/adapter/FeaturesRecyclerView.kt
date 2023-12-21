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
import com.capstone.terratani.ui.detection.DetectionCropActivity
import com.capstone.terratani.ui.detection.DetectionFertilizerActivity
import com.capstone.terratani.ui.detection.DetectionSoilActivity
import com.capstone.terratani.ui.detection.PredictionYieldActivity

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

        // Navigate to Detection and Prediction
        holder.itemView.findViewById<CardView>(R.id.card_features).setOnClickListener {
            if (position == 0) {
                val intent = Intent(holder.itemView.context, DetectionSoilActivity::class.java)
                holder.itemView.context.startActivity(intent)
            } else if (position == 1){
                val intent = Intent(holder.itemView.context, DetectionCropActivity::class.java)
                holder.itemView.context.startActivity(intent)
            } else if (position == 2) {
                val intent = Intent(holder.itemView.context, DetectionFertilizerActivity::class.java)
                holder.itemView.context.startActivity(intent)
            } else if (position == 3) {
                val intent = Intent(holder.itemView.context, PredictionYieldActivity::class.java)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}