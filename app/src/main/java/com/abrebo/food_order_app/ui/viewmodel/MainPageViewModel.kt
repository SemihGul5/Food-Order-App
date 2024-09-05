package com.abrebo.food_order_app.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abrebo.food_order_app.data.model.Foods
import com.abrebo.food_order_app.data.repo.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainPageViewModel @Inject constructor(var repository: Repository) :ViewModel() {

    var foodList=MutableLiveData<List<Foods>>()

    init {
        foodUpload()
    }

    fun foodUpload(){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                foodList.value=repository.foodsUpload()
            }catch (e:Exception){}
        }
    }


}