package com.abrebo.food_order_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.abrebo.food_order_app.data.model.Foods
import com.abrebo.food_order_app.data.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailPageViewModel @Inject constructor(var repository: Repository,var mainPageViewModel: MainPageViewModel) :ViewModel() {

    fun addToCart(yemekAdi:String,yemekResimAdi:String,yemekFiyat:Int,yemekSiparisAdet:Int,kullaniciAdi:String){
        CoroutineScope(Dispatchers.Main).launch {
            repository.addToCart(yemekAdi, yemekResimAdi, yemekFiyat, yemekSiparisAdet, kullaniciAdi)

        }
    }
    fun saveFoodFavorites(foods: Foods){
        mainPageViewModel.saveFoodFavorites(foods)
    }
    fun deleteFoodFromFavorites(foods: Foods){
        mainPageViewModel.deleteFoodFromFavorites(foods)
    }
}