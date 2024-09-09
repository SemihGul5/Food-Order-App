package com.abrebo.food_order_app.data.repo

import com.abrebo.food_order_app.data.datasource.Datasource
import com.abrebo.food_order_app.data.model.CartFood
import com.abrebo.food_order_app.data.model.Foods

class Repository(var datasource: Datasource) {


    suspend fun foodsUpload(): List<Foods> = datasource.foodsUpload()
    suspend fun addToCart(yemekAdi:String,yemekResimAdi:String,yemekFiyat:Int,yemekSiparisAdet:Int,kullaniciAdi:String) =
        datasource.addToCart(yemekAdi, yemekResimAdi, yemekFiyat, yemekSiparisAdet, kullaniciAdi)
    suspend fun getFoodInTheCart(kullaniciAdi: String) : List<CartFood> = datasource.getFoodInTheCart(kullaniciAdi)
    suspend fun deleteFoodFromCart(sepetYemekId:Int,kullaniciAdi: String) = datasource.deleteFoodFromCart(sepetYemekId, kullaniciAdi)
    suspend fun search(word:String): List<Foods> = datasource.search(word)
    suspend fun saveFoodFavorites(foods: Foods) = datasource.saveFoodFavorites(foods)
    suspend fun getFoodsFromFavorites():List<Foods> = datasource.getFoodsFromFavorites()
    suspend fun deleteFoodFromFavorites(foods: Foods) =datasource.deleteFoodFromFavorites(foods)

}