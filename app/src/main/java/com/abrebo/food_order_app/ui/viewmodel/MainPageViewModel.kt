package com.abrebo.food_order_app.ui.viewmodel

import android.widget.RadioButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abrebo.food_order_app.data.model.Foods
import com.abrebo.food_order_app.data.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MainPageViewModel @Inject constructor(var repository: Repository) :ViewModel() {
    var foodList=MutableLiveData<List<Foods>>()
    var favFoodList=MutableLiveData<List<Foods>>()
    private var lowFirst=0
    private var hightFirst=0
    private var recFirst=0


    init {
        foodUpload()
        getFoodsFromFavorites()
    }

    fun foodUpload(){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                foodList.value=repository.foodsUpload()
                recFirst=foodList.value!![0].yemek_fiyat
            }catch (e:Exception){}
        }
    }

    fun search(word:String){
        try {
            CoroutineScope(Dispatchers.Main).launch {
                foodList.value=repository.search(word)
            }
        }catch (e:Exception){
            foodUpload()
        }
    }

    fun saveFoodFavorites(foods: Foods){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                repository.saveFoodFavorites(foods)
                getFoodsFromFavorites()
            }catch (e:Exception){
            }

        }
    }
    fun deleteFoodFromFavorites(foods: Foods){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                repository.deleteFoodFromFavorites(foods)
                getFoodsFromFavorites()
            }catch (e:Exception){}

        }
    }

    fun getFoodsFromFavorites(){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                favFoodList.value = repository.getFoodsFromFavorites()
            }catch (e:Exception){
                favFoodList.value= listOf()
            }

        }
    }

    fun sortedByLowPrice(){
        CoroutineScope(Dispatchers.Main).launch {
            val sort = repository.foodsUpload().sortedWith(compareBy { it.yemek_fiyat })
            foodList.value=sort
            lowFirst=foodList.value!![0].yemek_fiyat
        }
    }
    fun sortedByHighPrice(){
        CoroutineScope(Dispatchers.Main).launch {
            val sort = repository.foodsUpload().sortedWith(compareByDescending { it.yemek_fiyat })
            foodList.value=sort
            hightFirst=foodList.value!![0].yemek_fiyat
        }
    }
    fun getBottomSheetSortCase(radioHighPrice: RadioButton, radioRecommended: RadioButton, radioLowPrice: RadioButton){
        val first= foodList.value!![0].yemek_fiyat
        if (first==recFirst){
            radioRecommended.isChecked=true
        }else if (first==lowFirst){
            radioLowPrice.isChecked=true
        }else if (first==hightFirst){
            radioHighPrice.isChecked=true
        }

    }





}