package com.abrebo.food_order_app.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.abrebo.food_order_app.R
import com.abrebo.food_order_app.data.model.Foods
import com.abrebo.food_order_app.databinding.FragmentProductDetailBinding
import com.abrebo.food_order_app.ui.viewmodel.ProductDetailPageViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailPageFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding
    private lateinit var viewModel:ProductDetailPageViewModel
    private var piece=1
    private var totalPrice=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp:ProductDetailPageViewModel by viewModels()
        viewModel=temp
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentProductDetailBinding.inflate(inflater, container, false)

        val bundle=ProductDetailPageFragmentArgs.fromBundle(requireArguments())
        val food=bundle.food
        val price=food.yemek_fiyat
        totalPrice=price*piece
        handleFavorite(bundle.isFavorite)


        val uri="http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"
        Glide.with(requireContext()).load(uri)
            .override(3000,2500)
            .into(binding.imageViewFood)
        binding.textViewFoodName.text=food.yemek_adi
        binding.textViewFoodPrice.text="${food.yemek_fiyat}₺"
        binding.textViewFoodPiece.text= piece.toString()
        binding.textViewTotalPrice.text="${totalPrice}₺"


        binding.imageViewDecreasePiece.setOnClickListener {
            if (piece<=1){
                Toast.makeText(requireContext(),"Adet daha fazla azaltılamaz!",Toast.LENGTH_SHORT).show()
            }else{
                handlePiece(false,food)
            }
        }
        binding.imageViewIncreasePiece.setOnClickListener {
            handlePiece(true,food)
        }

        binding.imageViewBack.setOnClickListener {

        }
        binding.imageViewFavorite.setOnClickListener {

        }
        binding.buttonAddToCart.setOnClickListener {
            viewModel.addToCart(food.yemek_adi,food.yemek_resim_adi,totalPrice,piece,"semih_gul")
            Toast.makeText(requireContext(),"${piece} adet ${food.yemek_adi} ürünü sepete eklendi.",Toast.LENGTH_SHORT).show()
        }





        return binding.root
    }

    private fun handleFavorite(favorite: Boolean) {
        if (favorite){
            binding.imageViewFavorite.setImageResource(R.drawable.baseline_favorite_white_30)
        }else{
            binding.imageViewFavorite.setImageResource(R.drawable.baseline_favorite_border_white_30)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handlePiece(increasePiece:Boolean, food:Foods){
        if (increasePiece){
            piece++
        }else{
            piece--
        }
        totalPrice=piece*food.yemek_fiyat
        binding.textViewFoodPiece.text=piece.toString()
        binding.textViewTotalPrice.text="${totalPrice}₺"
    }

}