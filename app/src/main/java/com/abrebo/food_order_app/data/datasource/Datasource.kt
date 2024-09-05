package com.abrebo.food_order_app.data.datasource

import com.abrebo.food_order_app.data.model.Foods
import com.abrebo.food_order_app.retrofit.FoodsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Datasource(var foodsDao: FoodsDao) {

    suspend fun foodsUpload(): List<Foods> =
        withContext(Dispatchers.IO){
            return@withContext foodsDao.foodUpload().yemekler
        }




}