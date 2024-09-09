package com.abrebo.food_order_app.ui.viewmodel

import androidx.lifecycle.LiveData
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



    init {
        foodUpload()
        getFoodsFromFavorites()
    }

    fun foodUpload(){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                foodList.value=repository.foodsUpload()
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




}