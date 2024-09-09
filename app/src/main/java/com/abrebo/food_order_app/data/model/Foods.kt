package com.abrebo.food_order_app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favoriler")
data class Foods(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "yemek_id") var yemek_id:Int,
                 @ColumnInfo(name = "yemek_adi") var yemek_adi:String,
                 @ColumnInfo(name = "yemek_resim_adi") var yemek_resim_adi:String,
                 @ColumnInfo(name = "yemek_fiyat") var yemek_fiyat:Int):Serializable {
}