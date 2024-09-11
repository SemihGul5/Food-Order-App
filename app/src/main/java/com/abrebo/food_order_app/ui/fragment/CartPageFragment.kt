package com.abrebo.food_order_app.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.abrebo.food_order_app.R
import com.abrebo.food_order_app.databinding.FragmentCartPageBinding
import com.abrebo.food_order_app.ui.adapter.CartAdapter
import com.abrebo.food_order_app.ui.viewmodel.CartPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartPageFragment : Fragment() {
    private lateinit var binding:FragmentCartPageBinding
    private lateinit var viewModel:CartPageViewModel
    private var isNull=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp:CartPageViewModel by viewModels()
        viewModel=temp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_cart_page, container, false)
        binding.viewModel=viewModel
        binding.lifecycleOwner=viewLifecycleOwner
        return binding.root
    }



    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cartFoodList.observe(viewLifecycleOwner) { foodList ->
            viewModel.isCartEmpty.observe(viewLifecycleOwner) { isEmpty ->
                isNull=isEmpty
                if (isEmpty) {
                    binding.animationViewCartLottie.playAnimation()
                    binding.animationViewCartLottie.loop(true)
                } else {
                    binding.animationViewCartLottie.cancelAnimation()
                    val adapter = CartAdapter(requireContext(), foodList, viewModel)
                    binding.recyclerViewCart.adapter = adapter
                }
                handleVisible(isEmpty)
            }
        }
        setupToolbarMenu()


    }
    override fun onResume() {
        super.onResume()
        viewModel.getFoodInTheCart()
    }
    fun handleVisible(isNull: Boolean) {
        if (isNull) {
            binding.linearLayout.visibility = View.GONE
            binding.recyclerViewCart.visibility = View.GONE
            binding.animationViewCartLottie.visibility = View.VISIBLE
        } else {
            binding.linearLayout.visibility = View.VISIBLE
            binding.recyclerViewCart.visibility = View.VISIBLE
            binding.animationViewCartLottie.visibility = View.GONE
        }
    }

    private fun setupToolbarMenu(){
        binding.materialToolbar3.setOnMenuItemClickListener { item->
            if (item.itemId==R.id.clearCart){
                if (!isNull){
                    viewModel.deleteAllFoodFromCart("semih_gul")
                }else{
                    Toast.makeText(requireContext(),"Sepet boş",Toast.LENGTH_SHORT).show()
                }
            }
            return@setOnMenuItemClickListener true
        }
    }
}