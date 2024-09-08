package com.abrebo.food_order_app.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abrebo.food_order_app.data.model.CartFood
import com.abrebo.food_order_app.data.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartPageViewModel @Inject constructor(var repository: Repository) :ViewModel() {

    var cartFoodList= MutableLiveData<List<CartFood>>()

    init {
        getFoodInTheCart()
    }

    fun getFoodInTheCart(){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                cartFoodList.value=repository.getFoodInTheCart("semih_gul")
            }catch (e:Exception){
                cartFoodList.value= listOf()
            }

        }
    }
    fun deleteFoodFromCart(sepetYemekId:Int,kullaniciAdi: String){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                repository.deleteFoodFromCart(sepetYemekId, kullaniciAdi)
                getFoodInTheCart()
            }catch (e:Exception){
                cartFoodList.value= listOf()
            }

        }

    }





}