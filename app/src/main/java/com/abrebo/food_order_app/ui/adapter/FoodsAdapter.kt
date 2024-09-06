package com.abrebo.food_order_app.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.abrebo.food_order_app.data.model.Foods
import com.abrebo.food_order_app.databinding.FragmentMainPageBinding
import com.abrebo.food_order_app.databinding.ProductLayoutBinding
import com.abrebo.food_order_app.ui.fragment.MainPageFragmentDirections
import com.bumptech.glide.Glide

class FoodsAdapter(var mContext:Context,var foodsList: List<Foods>):RecyclerView.Adapter<FoodsAdapter.FoodsHolder>() {

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

        binding.productCard.setOnClickListener {
            val gecis=MainPageFragmentDirections.actionMainPageFragmentToProductDetailPageFragment(food)
            Navigation.findNavController(it).navigate(gecis)

        }
        binding.imageViewFavorite.setOnClickListener {
            Toast.makeText(mContext,"Favorilere eklendi",Toast.LENGTH_SHORT).show()

        }
        binding.imageViewAddCart.setOnClickListener {
            Toast.makeText(mContext,food.yemek_adi,Toast.LENGTH_SHORT).show()

        }




    }
}