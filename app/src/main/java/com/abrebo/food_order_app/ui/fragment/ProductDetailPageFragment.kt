package com.abrebo.food_order_app.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.abrebo.food_order_app.R
import com.abrebo.food_order_app.databinding.FragmentProductDetailBinding
import com.abrebo.food_order_app.ui.viewmodel.ProductDetailPageViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailPageFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding
    private lateinit var viewModel:ProductDetailPageViewModel
    private var isFavorite=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp:ProductDetailPageViewModel by viewModels()
        viewModel=temp

    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_product_detail, container, false)
        binding.viewModel=viewModel
        binding.lifecycleOwner=viewLifecycleOwner
        val bundle=ProductDetailPageFragmentArgs.fromBundle(requireArguments())
        binding.food=bundle.food
        viewModel.initFood(bundle.food)
        isFavorite=bundle.isFavorite
        viewModel.initFavorite(isFavorite)


        viewModel.handleAnim.observe(viewLifecycleOwner){
            handleAnim(it)
        }
        viewModel.handleFavorite.observe(viewLifecycleOwner){
            handleFavorite(it)
        }


        val uri="http://kasimadalan.pe.hu/yemekler/resimler/${bundle.food.yemek_resim_adi}"
        Glide.with(requireContext()).load(uri)
            .override(3000,2500)
            .into(binding.imageViewFood)









        return binding.root
    }

    private fun handleAnim(isAdd:Boolean){
        if (isAdd){
            binding.buttonAddToCart.visibility=View.GONE
            binding.textViewTotalPrice.visibility=View.GONE
            binding.animationViewAddCartLottie.visibility=View.VISIBLE
        }else{
            binding.animationViewAddCartLottie.visibility=View.GONE
            binding.buttonAddToCart.visibility=View.VISIBLE
            binding.textViewTotalPrice.visibility=View.VISIBLE
        }
    }

    private fun handleFavorite(favorite: Boolean) {
        if (favorite){
            binding.imageViewFavorite.setImageResource(R.drawable.baseline_favorite_white_30)
        }else{
            binding.imageViewFavorite.setImageResource(R.drawable.baseline_favorite_border_white_30)
        }
    }



}