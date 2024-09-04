package com.abrebo.food_order_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abrebo.food_order_app.R
import com.abrebo.food_order_app.databinding.FragmentCartPageBinding

class CartPageFragment : Fragment() {
    private lateinit var binding:FragmentCartPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentCartPageBinding.inflate(inflater, container, false)



        return binding.root
    }

}