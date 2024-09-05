package com.abrebo.food_order_app.retrofit

import com.abrebo.food_order_app.data.model.FoodsResponse
import retrofit2.http.GET

interface FoodsDao {

    //http://kasimadalan.pe.hu/
    //yemekler/tumYemekleriGetir.php

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun foodUpload():FoodsResponse


}