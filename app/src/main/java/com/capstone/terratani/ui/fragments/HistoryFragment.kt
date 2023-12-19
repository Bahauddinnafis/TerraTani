package com.capstone.terratani.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.terratani.R
import com.capstone.terratani.databinding.FragmentHistoryBinding
import com.capstone.terratani.ui.adapter.HistoryRecyclerView

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistoryRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.rvHistory
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        adapter = HistoryRecyclerView()
        recyclerView.adapter = adapter

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}