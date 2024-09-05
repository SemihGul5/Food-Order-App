package com.abrebo.food_order_app.data.repo

import com.abrebo.food_order_app.data.datasource.Datasource
import com.abrebo.food_order_app.data.model.Foods

class Repository(var datasource: Datasource) {


    suspend fun foodsUpload(): List<Foods> = datasource.foodsUpload()
}