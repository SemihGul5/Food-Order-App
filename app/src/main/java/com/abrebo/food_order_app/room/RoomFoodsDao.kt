package com.abrebo.food_order_app.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.abrebo.food_order_app.data.model.Foods

@Dao
interface RoomFoodsDao {

    @Query("select * from favoriler")
    suspend fun getFoodsFromFavorites(): List<Foods>

    @Insert
    suspend fun saveFoodFavorites(foods: Foods)

    @Delete
    suspend fun deleteFoodFromFavorites(foods: Foods)
}