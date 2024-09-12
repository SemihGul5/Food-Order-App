package com.abrebo.food_order_app.data.datasource

import com.abrebo.food_order_app.data.model.CartFood
import com.abrebo.food_order_app.data.model.Foods
import com.abrebo.food_order_app.retrofit.FoodsDao
import com.abrebo.food_order_app.room.RoomFoodsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Datasource(var foodsDao: FoodsDao, var roomFoodsDao: RoomFoodsDao) {

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

    suspend fun deleteFoodFromCart(sepetYemekId:Int,kullaniciAdi: String){
        foodsDao.deleteFoodFromCart(sepetYemekId, kullaniciAdi)
    }


    suspend fun search(word:String): List<Foods> = withContext(Dispatchers.IO){
        val searchList= foodsDao.foodUpload().yemekler.filter {
            it.yemek_adi
                .lowercase()
                .contains(word.lowercase())
        }
        return@withContext searchList
    }

    suspend fun saveFoodFavorites(foods: Foods){
        roomFoodsDao.saveFoodFavorites(foods)
    }
    suspend fun getFoodsFromFavorites():List<Foods> = withContext(Dispatchers.IO){
        return@withContext roomFoodsDao.getFoodsFromFavorites()
    }
    suspend fun deleteFoodFromFavorites(foods: Foods) {
        roomFoodsDao.deleteFoodFromFavorites(foods)
    }






}