package com.abrebo.food_order_app.ui.viewmodel

import android.animation.Animator
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.abrebo.food_order_app.R
import com.abrebo.food_order_app.data.model.CartFood
import com.abrebo.food_order_app.data.model.Foods
import com.abrebo.food_order_app.data.repo.Repository
import com.abrebo.food_order_app.util.switch
import com.airbnb.lottie.LottieAnimationView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartPageViewModel @Inject constructor(var repository: Repository) :ViewModel() {
    var cartFoodList= MutableLiveData<List<CartFood>>()
    var totalPrice=MutableLiveData<Int>()
    var isCartEmpty = MutableLiveData<Boolean>()
    var cartFoodList2=ArrayList<CartFood>()
    var idList=ArrayList<Int>()

    init {
        getFoodInTheCart()
        calcTotalPrice()
    }

    fun getFoodInTheCart(){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                cartFoodList.value=repository.getFoodInTheCart("semih_gul")
                processCartFoodList(cartFoodList.value!!)
                calcTotalPrice()

            }catch (e:Exception){
                cartFoodList.value= listOf()
                totalPrice.value=0
                isCartEmpty.value = true
            }

        }
    }
    fun deleteFoodFromCart(yemekAdi: String, kullaniciAdi: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                cartFoodList2.forEach {
                    if (it.yemek_adi==yemekAdi){
                        repository.deleteFoodFromCart(it.sepet_yemek_id,kullaniciAdi)
                    }
                }

                getFoodInTheCart()
            }catch (e:Exception){
                cartFoodList.value= listOf()
                totalPrice.value=0
                isCartEmpty.value=true
            }
        }
    }


    fun deleteAllFoodFromCart(kullaniciAdi: String){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                cartFoodList.value?.forEach {
                    idList.forEach {
                        repository.deleteFoodFromCart(it,kullaniciAdi)
                    }
                }
                getFoodInTheCart()
            }catch (e:Exception){
                cartFoodList.value= listOf()
                totalPrice.value=0
                isCartEmpty.value=true
            }
        }
    }
    fun calcTotalPrice(){
        var total=0
        if (!cartFoodList.value.isNullOrEmpty()){
            cartFoodList.value?.forEach {
                total+=it.yemek_fiyat
                totalPrice.value=total
            }
        }else{
            totalPrice.value=0
        }
    }
    fun buttonCartConfirmClicked(animationViewCartLottie:LottieAnimationView){
        isCartEmpty.value = true
        animationViewCartLottie.loop(false)
        animationViewCartLottie.maxWidth=200
        animationViewCartLottie.maxHeight=200
        animationViewCartLottie.setAnimation(R.raw.check_2)
        animationViewCartLottie.playAnimation()

        animationViewCartLottie.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                isCartEmpty.value = true
                animationViewCartLottie.setAnimation(R.raw.food_order)
                animationViewCartLottie.playAnimation()
                animationViewCartLottie.loop(true)
                deleteAllFoodFromCart("semih_gul")
            }
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }

    fun processCartFoodList(foodList: List<CartFood>) {
        isCartEmpty.value = false
        val foodMap = mutableMapOf<String, CartFood>()
        cartFoodList2.clear()
        idList.clear()
        for (food in foodList) {
            cartFoodList2.add(food)
            idList.add(food.sepet_yemek_id)
            val currentFood = foodMap[food.yemek_adi]
            if (currentFood != null) {
                currentFood.yemek_siparis_adet += food.yemek_siparis_adet
                currentFood.yemek_fiyat += food.yemek_fiyat

            } else {
                foodMap[food.yemek_adi] = CartFood(
                    food.sepet_yemek_id,
                    food.yemek_adi,
                    food.yemek_resim_adi,
                    food.yemek_fiyat,
                    food.yemek_siparis_adet,
                    "semih_gul"
                )
            }
        }
        cartFoodList.value = foodMap.values.toList()
    }

    fun backButtonClicked(it:View){
        Navigation.switch(it,R.id.action_cartPageFragment_to_mainPageFragment)
    }

}