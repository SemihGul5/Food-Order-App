package com.abrebo.food_order_app.data.repo

import com.abrebo.food_order_app.data.datasource.Datasource
import com.abrebo.food_order_app.data.model.CartFood
import com.abrebo.food_order_app.data.model.Foods

class Repository(var datasource: Datasource) {


    suspend fun foodsUpload(): List<Foods> = datasource.foodsUpload()
    suspend fun addToCart(yemekAdi:String,yemekResimAdi:String,yemekFiyat:Int,yemekSiparisAdet:Int,kullaniciAdi:String) =
        datasource.addToCart(yemekAdi, yemekResimAdi, yemekFiyat, yemekSiparisAdet, kullaniciAdi)
    suspend fun getFoodInTheCart(kullaniciAdi: String) : List<CartFood> = datasource.getFoodInTheCart(kullaniciAdi)





}