package com.abrebo.food_order_app.data.model

import java.io.Serializable

data class Foods(var yemek_id:Int,
                 var yemek_adi:String,
                 var yemek_resim_adi:String,
                 var yemek_fiyat:Int):Serializable {
}