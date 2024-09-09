package com.abrebo.food_order_app.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.abrebo.food_order_app.R
import com.abrebo.food_order_app.data.model.Foods
import com.abrebo.food_order_app.databinding.FragmentMainPageBinding
import com.abrebo.food_order_app.databinding.ProductLayoutBinding
import com.abrebo.food_order_app.ui.fragment.MainPageFragmentDirections
import com.abrebo.food_order_app.ui.viewmodel.MainPageViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class FoodsAdapter(var mContext:Context,
                   var foodsList: List<Foods>,
                   var viewModel:MainPageViewModel,
                   var favList:List<Foods>,
                  ):RecyclerView.Adapter<FoodsAdapter.FoodsHolder>() {

    inner class FoodsHolder(var binding:ProductLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodsHolder {
        val binding= ProductLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return FoodsHolder(binding)
    }

    override fun getItemCount(): Int {
        return foodsList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FoodsHolder, position: Int) {
        val food=foodsList.get(position)
        val binding=holder.binding

        val uri="http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"
        Glide.with(mContext).load(uri).into(binding.imageViewFood)
        binding.textViewFoodName.text=food.yemek_adi
        binding.textViewFoodPrice.text="${food.yemek_fiyat} ₺"


        val isFavorite = favList.any { it.yemek_adi == food.yemek_adi }
        if (isFavorite) {
            binding.imageViewFavorite.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            binding.imageViewFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
        }


        binding.productCard.setOnClickListener {
            val gecis=MainPageFragmentDirections.actionMainPageFragmentToProductDetailPageFragment(food,isFavorite)
            Navigation.findNavController(it).navigate(gecis)

        }
        binding.imageViewFavorite.setOnClickListener {
            if (isFavorite) {
                viewModel.deleteFoodFromFavorites(food)
                Snackbar.make(it, "Favorilerden silindi", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.WHITE)
                    .setTextColor(Color.BLACK)
                    .show()
            } else {
                viewModel.saveFoodFavorites(food)
                Snackbar.make(it, "Favorilere eklendi", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.WHITE)
                    .setTextColor(Color.BLACK)
                    .show()
            }
        }



        binding.imageViewAddCart.setOnClickListener {
            Toast.makeText(mContext,food.yemek_adi,Toast.LENGTH_SHORT).show()

        }

    }
}