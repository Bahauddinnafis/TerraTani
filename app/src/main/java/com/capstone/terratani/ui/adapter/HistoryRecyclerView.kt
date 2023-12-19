package com.capstone.terratani.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.terratani.R

class HistoryRecyclerView: RecyclerView.Adapter<HistoryRecyclerView.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryRecyclerView.MyViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}