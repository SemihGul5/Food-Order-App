package com.abrebo.food_order_app.ui.fragment

import android.animation.Animator
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.abrebo.food_order_app.MainActivity
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
    private var isNull=false
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



    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cartFoodList.observe(viewLifecycleOwner) { foodList ->
            if (foodList.isNullOrEmpty()){
                isNull=true
                handleVisible(true)
                binding.animationViewCartLottie.playAnimation()
                binding.animationViewCartLottie.loop(true)
            }else{
                isNull=false
                handleVisible(false)
                viewModel.totalPrice.observe(viewLifecycleOwner){
                    binding.cartTotal.text="${it.toString()}₺"
                }
                val foodMap = mutableMapOf<String, CartFood>()
                for (food in foodList) {
                    val currentFood = foodMap[food.yemek_adi]
                    if (currentFood != null) {
                        currentFood.yemek_siparis_adet +=food.yemek_siparis_adet
                        currentFood.yemek_fiyat += food.yemek_fiyat
                    } else {
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

        }
        binding.buttonCartConfirm.setOnClickListener {
            handleVisible(true)
            binding.animationViewCartLottie.loop(false)
            binding.animationViewCartLottie.maxWidth=200
            binding.animationViewCartLottie.maxHeight=200
            binding.animationViewCartLottie.setAnimation(R.raw.check_2)
            binding.animationViewCartLottie.playAnimation()

            binding.animationViewCartLottie.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    handleVisible(true)
                    binding.animationViewCartLottie.setAnimation(R.raw.food_order)
                    binding.animationViewCartLottie.playAnimation()
                    binding.animationViewCartLottie.loop(true)
                    viewModel.deleteAllFoodFromCart("semih_gul")
                }
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
        }

        binding.materialToolbar3.setOnMenuItemClickListener { item->

            if (item.itemId==R.id.clearCart){
                if (!isNull){
                    viewModel.deleteAllFoodFromCart("semih_gul")

                }else{
                    Toast.makeText(requireContext(),"Sepet boş",Toast.LENGTH_SHORT).show()
                }

            }
            return@setOnMenuItemClickListener true
        }


    }
    override fun onResume() {
        super.onResume()
        viewModel.getFoodInTheCart()
    }
    fun handleVisible(isNull:Boolean){
        if (isNull){
            binding.linearLayout.visibility=View.GONE
            binding.recyclerViewCart.visibility=View.GONE
            binding.animationViewCartLottie.visibility=View.VISIBLE
        }else{
            binding.linearLayout.visibility=View.VISIBLE
            binding.recyclerViewCart.visibility=View.VISIBLE
            binding.animationViewCartLottie.visibility=View.GONE
        }
    }
}