package com.abrebo.food_order_app.ui.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.abrebo.food_order_app.R
import com.abrebo.food_order_app.databinding.FragmentMainPageBinding
import com.abrebo.food_order_app.ui.adapter.FoodsAdapter
import com.abrebo.food_order_app.ui.viewmodel.MainPageViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageFragment : Fragment() {
    private lateinit var binding:FragmentMainPageBinding
    private lateinit var viewModel:MainPageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp:MainPageViewModel by viewModels()
        viewModel=temp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_main_page, container, false)
        binding.viewModel=viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        viewModel.foodList.observe(viewLifecycleOwner){foodList->
            viewModel.favFoodList.observe(viewLifecycleOwner){
                val adapter=FoodsAdapter(requireContext(),foodList,viewModel,it,1)
                binding.recyclerView.adapter=adapter
            }
        }
        setupToolbarMenu()
        return binding.root
    }

    private fun setupToolbarMenu() {
        binding.materialToolbar.setOnMenuItemClickListener { item ->
            val id = item.itemId
            if (id == R.id.app_bar_search) {
                handleSearchMenuItem(item)
            }else if (id == R.id.app_bar_sort) {
                handleSortMenuItem()
            }
            false
        }
    }

    private fun handleSortMenuItem() {
        val dialog = BottomSheetDialog(requireContext())
        val bottomSheet = layoutInflater.inflate(R.layout.main_page_bottom_sheet_dialog, null)
        val radioHighPrice = bottomSheet.findViewById<RadioButton>(R.id.radioHighPrice)
        val radioRecommended = bottomSheet.findViewById<RadioButton>(R.id.radioButtonRecommended)
        val radioLowPrice = bottomSheet.findViewById<RadioButton>(R.id.radioButtonLowPrice)
        viewModel.getBottomSheetSortCase(radioHighPrice,radioRecommended,radioLowPrice)

        radioRecommended?.setOnClickListener {
            dialog.dismiss()
            viewModel.foodUpload()
        }

        radioLowPrice?.setOnClickListener {
            dialog.dismiss()
            viewModel.sortedByLowPrice()
        }

        radioHighPrice?.setOnClickListener {
            dialog.dismiss()
            viewModel.sortedByHighPrice()
        }
        dialog.setContentView(bottomSheet)
        dialog.show()
    }

    private fun handleSearchMenuItem(item: MenuItem) {
        val searchView = item.actionView as SearchView
        val searchEditText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchEditText.setTextColor(Color.WHITE)
        searchEditText.setHintTextColor(Color.WHITE)
        searchView.setQuery("", false)
        searchView.requestFocus()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(!query.isNullOrEmpty()){
                    viewModel.search(query)
                }else{
                    viewModel.foodUpload()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(!newText.isNullOrEmpty()){
                    viewModel.search(newText)
                }else{
                    viewModel.foodUpload()
                }
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFoodsFromFavorites()
    }

}