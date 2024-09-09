package com.abrebo.food_order_app.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abrebo.food_order_app.data.model.Foods

@Database(entities = [Foods::class], version = 1)
abstract class Db:RoomDatabase() {
    abstract fun getRoomFoodsDao():RoomFoodsDao
}