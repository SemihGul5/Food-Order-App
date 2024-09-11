package com.abrebo.food_order_app.ui.viewmodel

import android.animation.Animator
import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.abrebo.food_order_app.R
import com.abrebo.food_order_app.data.model.Foods
import com.abrebo.food_order_app.data.repo.Repository
import com.abrebo.food_order_app.util.makeWhiteSnackbar
import com.abrebo.food_order_app.util.switch
import com.airbnb.lottie.LottieAnimationView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailPageViewModel @Inject constructor(var repository: Repository,
                                                     var mainPageViewModel: MainPageViewModel) :ViewModel() {
    var handleAnim=MutableLiveData<Boolean>()
    var piece=MutableLiveData<Int>()
    var totalPrice=MutableLiveData<Int>()
    var food1=MutableLiveData<Foods>()
    var handleFavorite=MutableLiveData<Boolean>()

    init {
        piece.value=1
        totalPrice.value= 0
        handleFavorite.value=false
    }

    fun initFood(food:Foods){
        food1.value=food
        piece.value=1
        totalPrice.value=food.yemek_fiyat
    }
    fun initFavorite(isFavorite:Boolean){
        handleFavorite.value=isFavorite
    }
    fun addToCart(yemekAdi:String,yemekResimAdi:String,yemekFiyat:Int,yemekSiparisAdet:Int,kullaniciAdi:String){
        CoroutineScope(Dispatchers.Main).launch {
            repository.addToCart(yemekAdi, yemekResimAdi, yemekFiyat, yemekSiparisAdet, kullaniciAdi)
        }
    }
    fun saveFoodFavorites(foods: Foods){
        mainPageViewModel.saveFoodFavorites(foods)
    }
    fun deleteFoodFromFavorites(foods: Foods){
        mainPageViewModel.deleteFoodFromFavorites(foods)
    }
    fun imageViewBackClicked(it: View){
        Navigation.switch(it, R.id.action_productDetailPageFragment_to_mainPageFragment)
    }

    fun imageViewDecreasePieceClicked(food:Foods,it:View){
        try {
            if (piece.value!! <=1){
                it.makeWhiteSnackbar("Adet daha fazla azaltılamaz!")
            }else{
                handlePiece(false,food)
            }
        }catch (e:Exception){
            piece.value=1
        }

    }

    fun imageViewIncreasePieceClicked(food:Foods){
        handlePiece(true,food)
    }

    @SuppressLint("SetTextI18n")
    private fun handlePiece(increasePiece:Boolean, food:Foods){
        try {
            if (increasePiece){
                piece.value = piece.value?.plus(1)
            }else{
                piece.value = piece.value?.minus(1)
            }
            totalPrice.value=piece.value!!*food.yemek_fiyat
        }catch (e:Exception){
            piece.value=1
            totalPrice.value=food.yemek_fiyat
        }
    }

    fun imageViewFavoriteClicked(food: Foods,it:View,imageViewFavorite:ImageView){
        if (handleFavorite.value==true){
            deleteFoodFromFavorites(food)
            handleFavorite.value=false
            it.makeWhiteSnackbar("Favorilerden silindi")
            imageViewFavorite.setImageResource(R.drawable.baseline_favorite_border_white_30)
        }else{
            saveFoodFavorites(food)
            handleFavorite.value=true
            it.makeWhiteSnackbar("Favorilere eklendi")
            imageViewFavorite.setImageResource(R.drawable.baseline_favorite_white_30)
        }
    }













    fun buttonAddToCartClicked(food: Foods,totalPrice:Int,piece:Int,animationViewAddCartLottie:LottieAnimationView,it:View){
        addToCart(food.yemek_adi,food.yemek_resim_adi,totalPrice,piece,"semih_gul")
        handleAnim.value=true
        animationViewAddCartLottie.playAnimation()
        animationViewAddCartLottie.addAnimatorListener(object: Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator) {}
            override fun onAnimationEnd(p0: Animator) {
                it.makeWhiteSnackbar("${piece} adet ${food.yemek_adi} ürünü sepete eklendi.")
                handleAnim.value=false
            }
            override fun onAnimationCancel(p0: Animator) {}
            override fun onAnimationRepeat(p0: Animator) {}
        })
    }

}