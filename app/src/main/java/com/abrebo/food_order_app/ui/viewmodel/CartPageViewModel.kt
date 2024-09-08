package com.abrebo.food_order_app.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abrebo.food_order_app.data.model.CartFood
import com.abrebo.food_order_app.data.repo.Repository
import com.google.android.material.snackbar.Snackbar
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
    fun deleteFoodFromCart(yemekAdi:String,kullaniciAdi: String){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                cartFoodList.value?.forEach {
                    if (it.yemek_adi==yemekAdi){
                        repository.deleteFoodFromCart(it.sepet_yemek_id, kullaniciAdi)
                    }
                }
                getFoodInTheCart()

            }catch (e:Exception){
                cartFoodList.value= listOf()
            }

        }
    }
    fun deleteAllFoodFromCart(kullaniciAdi: String){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                cartFoodList.value?.forEach {
                    repository.deleteFoodFromCart(it.sepet_yemek_id,kullaniciAdi)
                }
                getFoodInTheCart()
            }catch (e:Exception){
                cartFoodList.value= listOf()
            }
        }
    }





}