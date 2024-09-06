package com.abrebo.food_order_app.di

import com.abrebo.food_order_app.data.datasource.Datasource
import com.abrebo.food_order_app.data.repo.Repository
import com.abrebo.food_order_app.retrofit.ApiUtils
import com.abrebo.food_order_app.retrofit.FoodsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatasource(foodsDao: FoodsDao):Datasource{
        return Datasource(foodsDao)
    }

    @Provides
    @Singleton
    fun proivdeFoodsDao():FoodsDao{
        return ApiUtils.getFoodsDao()
    }

    @Provides
    @Singleton
    fun provideRepository(datasource: Datasource):Repository{
        return Repository(datasource)
    }
















}