package com.abrebo.food_order_app.util

import android.graphics.Color
import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar

fun Navigation.switch(it:View,id:Int){
    findNavController(it).navigate(id)
}

fun Navigation.switch(it:View,navDirections: NavDirections){
    findNavController(it).navigate(navDirections)
}

fun View.makeWhiteSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(Color.WHITE)
        .setTextColor(Color.BLACK)
        .show()
}
