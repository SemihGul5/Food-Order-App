package com.abrebo.food_order_app.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abrebo.food_order_app.R
import com.abrebo.food_order_app.data.model.CartFood
import com.abrebo.food_order_app.databinding.CartCardLayoutBinding
import com.abrebo.food_order_app.ui.viewmodel.CartPageViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class CartAdapter(var mContext:Context,var foods:List<CartFood>, var viewModel:CartPageViewModel):RecyclerView.Adapter<CartAdapter.CartHolder>() {

    inner class CartHolder(var binding: CartCardLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val binding: CartCardLayoutBinding=DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.cart_card_layout, parent, false)
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
        binding.food=food


        binding.imageViewDeleteFoodFromCart.setOnClickListener {
            Snackbar.make(it,"${food.yemek_adi} silinsin mi?",Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.WHITE)
                .setAction("Evet") {
                    viewModel.deleteFoodFromCart(food.yemek_adi, "semih_gul")
                    Toast.makeText(mContext,"${food.yemek_siparis_adet} adet ${food.yemek_adi} silindi",Toast.LENGTH_SHORT).show()
                }
                .show()
        }



    }


}