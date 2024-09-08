package com.abrebo.food_order_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.abrebo.food_order_app.R
import com.abrebo.food_order_app.data.model.CartFood
import com.abrebo.food_order_app.databinding.FragmentCartPageBinding
import com.abrebo.food_order_app.ui.adapter.CartAdapter
import com.abrebo.food_order_app.ui.viewmodel.CartPageViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartPageFragment : Fragment() {
    private lateinit var binding:FragmentCartPageBinding
    private lateinit var viewModel:CartPageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp:CartPageViewModel by viewModels()
        viewModel=temp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentCartPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFoodInTheCart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cartFoodList.observe(viewLifecycleOwner) { foodList ->

            val foodMap = mutableMapOf<String, CartFood>()
            for (food in foodList) {
                val currentFood = foodMap[food.yemek_adi]

                if (currentFood != null) {
                    // Eğer bu yemek zaten Map'te varsa adetini ve toplam fiyatını artır
                    currentFood.yemek_siparis_adet +=food.yemek_siparis_adet
                    currentFood.yemek_fiyat += food.yemek_fiyat
                } else {
                    // Eğer yoksa yeni bir nesne oluştur ve ekle
                    foodMap[food.yemek_adi] = CartFood(
                        food.sepet_yemek_id,
                        food.yemek_adi,
                        food.yemek_resim_adi,
                        food.yemek_fiyat,
                        food.yemek_siparis_adet,
                        "semih_gul")
                }
            }


            val finalFoodList = foodMap.values.toList()
            val adapter = CartAdapter(requireContext(), finalFoodList,viewModel)
            binding.recyclerViewCart.adapter = adapter
        }

        binding.materialToolbar3.setOnMenuItemClickListener { item->

            if (item.itemId==R.id.clearCart){
                viewModel.deleteAllFoodFromCart("semih_gul")
            }
            return@setOnMenuItemClickListener true
        }


    }

}