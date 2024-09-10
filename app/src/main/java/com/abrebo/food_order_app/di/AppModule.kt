package com.abrebo.food_order_app.di

import android.content.Context
import androidx.room.Room
import com.abrebo.food_order_app.data.datasource.Datasource
import com.abrebo.food_order_app.data.repo.Repository
import com.abrebo.food_order_app.retrofit.ApiUtils
import com.abrebo.food_order_app.retrofit.FoodsDao
import com.abrebo.food_order_app.room.Db
import com.abrebo.food_order_app.room.RoomFoodsDao
import com.abrebo.food_order_app.ui.viewmodel.MainPageViewModel
import com.abrebo.food_order_app.ui.viewmodel.ProductDetailPageViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatasource(foodsDao: FoodsDao,roomFoodsDao: RoomFoodsDao):Datasource{
        return Datasource(foodsDao,roomFoodsDao)
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
    @Provides
    @Singleton
    fun provideRoomFoodsDaoy(@ApplicationContext contex:Context):RoomFoodsDao{
        val db=Room.databaseBuilder(contex, Db::class.java,"favoriler.sqlite")
            .createFromAsset("favoriler.sqlite")
            .build()

        return db.getRoomFoodsDao()
    }

    @Provides
    @Singleton
    fun provideProductDetailPageViewModel(repository: Repository,mainPageViewModel: MainPageViewModel): ProductDetailPageViewModel{
        return ProductDetailPageViewModel(repository,mainPageViewModel)
    }
    @Provides
    @Singleton
    fun provideProductMainPageViewModel(repository: Repository): MainPageViewModel{
        return MainPageViewModel(repository)
    }
















}