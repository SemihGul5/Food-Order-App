package com.abrebo.food_order_app.retrofit

import com.abrebo.food_order_app.data.model.CRUDResponse
import com.abrebo.food_order_app.data.model.CartFoodResponse
import com.abrebo.food_order_app.data.model.FoodsResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodsDao {

    //http://kasimadalan.pe.hu/
    //yemekler/tumYemekleriGetir.php

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun foodUpload():FoodsResponse

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun addToCart(@Field("yemek_adi") yemekAdi:String,
                          @Field("yemek_resim_adi") yemekResimAdi:String,
                          @Field("yemek_fiyat") yemekFiyat:Int,
                          @Field("yemek_siparis_adet") yemekSiparisAdet:Int,
                          @Field("kullanici_adi") kullaniciAdi:String):CRUDResponse

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun getFoodInTheCart(@Field("kullanici_adi") kullaniciAdi: String):CartFoodResponse

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun deleteFoodFromCart(@Field("sepet_yemek_id") sepetYemekId:Int,
                                   @Field("kullanici_adi") kullaniciAdi: String):CRUDResponse


}