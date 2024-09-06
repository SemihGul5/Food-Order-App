package com.abrebo.food_order_app.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abrebo.food_order_app.data.model.CartFood
import com.abrebo.food_order_app.databinding.CartCardLayoutBinding
import com.abrebo.food_order_app.databinding.FragmentCartPageBinding
import com.bumptech.glide.Glide

class CartAdapter(var mContext:Context,var foods:List<CartFood>):RecyclerView.Adapter<CartAdapter.CartHolder>() {

    inner class CartHolder(var binding: CartCardLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val binding= CartCardLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CartHolder(binding)
    }

    override fun getItemCount(): Int {
        return foods.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        val food=foods.get(position)
        val binding=holder.binding
        val uri="http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"
        Glide.with(mContext).load(uri).into(binding.imageViewFood)
        binding.textViewFoodName.text=food.yemek_adi
        binding.textViewFoodPrice.text="Fiyat: ${food.yemek_fiyat/food.yemek_siparis_adet}₺"
        binding.textViewFoodPiece.text="Adet: ${food.yemek_siparis_adet}"
        binding.textViewFoodTotalPrice.text="${food.yemek_fiyat}₺"


        binding.imageViewDeleteFoodFromCart.setOnClickListener {

        }



    }
}