package com.capstone.terratani.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.terratani.R
import com.capstone.terratani.model.Treatment

class TreatmentRecyclerView(private val treatmentList: List<Treatment>): RecyclerView.Adapter<TreatmentRecyclerView.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.treatment_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTreatment = treatmentList[position]

        holder.itemView.findViewById<TextView>(R.id.tv_maintance).text = currentTreatment.title
    }

    override fun getItemCount(): Int {
        return treatmentList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

}