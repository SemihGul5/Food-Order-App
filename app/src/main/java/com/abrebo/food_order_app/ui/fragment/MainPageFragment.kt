package com.abrebo.food_order_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.abrebo.food_order_app.R
import com.abrebo.food_order_app.databinding.FragmentMainPageBinding
import com.abrebo.food_order_app.ui.adapter.FoodsAdapter
import com.abrebo.food_order_app.ui.viewmodel.MainPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageFragment : Fragment() {
    private lateinit var binding:FragmentMainPageBinding
    private lateinit var viewModel:MainPageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp:MainPageViewModel by viewModels()
        viewModel=temp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentMainPageBinding.inflate(inflater, container, false)



        viewModel.foodList.observe(viewLifecycleOwner){
            val adapter=FoodsAdapter(requireContext(),it)
            binding.recyclerView.adapter=adapter
        }

        return binding.root
    }

}