package com.abrebo.food_order_app.data.datasource

import com.abrebo.food_order_app.data.model.CartFood
import com.abrebo.food_order_app.data.model.Foods
import com.abrebo.food_order_app.retrofit.FoodsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Datasource(var foodsDao: FoodsDao) {

    suspend fun foodsUpload(): List<Foods> =
        withContext(Dispatchers.IO){
            return@withContext foodsDao.foodUpload().yemekler
        }

    suspend fun addToCart(yemekAdi:String,yemekResimAdi:String,yemekFiyat:Int,yemekSiparisAdet:Int,kullaniciAdi:String){
        foodsDao.addToCart(yemekAdi,yemekResimAdi,yemekFiyat,yemekSiparisAdet,kullaniciAdi)
    }

    suspend fun getFoodInTheCart(kullaniciAdi: String) : List<CartFood> =
        withContext(Dispatchers.IO){
            return@withContext foodsDao.getFoodInTheCart(kullaniciAdi).sepet_yemekler
        }




}