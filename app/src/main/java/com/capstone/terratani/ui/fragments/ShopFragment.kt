package com.capstone.terratani.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.terratani.R
import com.capstone.terratani.databinding.FragmentShopBinding
import com.capstone.terratani.dummy.DataDummy
import com.capstone.terratani.ui.adapter.CategoryRecyclerView
import com.capstone.terratani.ui.adapter.NewRecyclerView
import com.capstone.terratani.ui.adapter.ShopRecyclerView

class ShopFragment : Fragment() {
    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    // First RecyclerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShopRecyclerView

    // Second RecyclerView
    private lateinit var secondRecyclerView: RecyclerView
    private lateinit var secondAdapter: CategoryRecyclerView

    // Third RecyclerView
    private lateinit var thirdRecyclerView: RecyclerView
    private lateinit var thirdAdapter: NewRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        val view = binding.root

        // First RecyclerView
        recyclerView = binding.rvBlank
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        val imageShopList = DataDummy.imageShop
        adapter = ShopRecyclerView(imageShopList)
        recyclerView.adapter = adapter

        // Second RecyclerView
        secondRecyclerView = binding.rvCategory
        val secLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        secondRecyclerView.layoutManager = secLayoutManager

        val categoryData = DataDummy.category
        secondAdapter = CategoryRecyclerView(categoryData)
        secondRecyclerView.adapter = secondAdapter

        // Third RecyclerView
        thirdRecyclerView = binding.rvNew
        val thirdLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        thirdRecyclerView.layoutManager = thirdLayoutManager

        val newData = DataDummy.category
        thirdAdapter = NewRecyclerView(newData)
        thirdRecyclerView.adapter = thirdAdapter

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}