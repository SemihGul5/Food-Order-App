package com.abrebo.food_order_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.abrebo.food_order_app.R
import com.abrebo.food_order_app.databinding.FragmentFavoritesPageBinding
import com.abrebo.food_order_app.ui.adapter.FoodsAdapter
import com.abrebo.food_order_app.ui.viewmodel.MainPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesPageFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesPageBinding
    private lateinit var viewModel:MainPageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp:MainPageViewModel by viewModels()
        viewModel=temp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_favorites_page, container, false)


        viewModel.favFoodList.observe(viewLifecycleOwner){
            if (it.isNullOrEmpty()){
                handleVisible(true)
            }else{
                handleVisible(false)
                val adapter=FoodsAdapter(requireContext(),it,viewModel,it,2)
                binding.recyclerViewFavorites.adapter=adapter
            }
        }

        return binding.root
    }
    fun handleVisible(isNull:Boolean){
        if (isNull){
            binding.animationViewFavoriteLottie.visibility=View.VISIBLE
            binding.recyclerViewFavorites.visibility=View.GONE
        }else{
            binding.animationViewFavoriteLottie.visibility=View.GONE
            binding.recyclerViewFavorites.visibility=View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFoodsFromFavorites()
    }

}