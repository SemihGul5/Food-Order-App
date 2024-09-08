package com.abrebo.food_order_app.ui.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.abrebo.food_order_app.R
import com.abrebo.food_order_app.data.model.Foods
import com.abrebo.food_order_app.databinding.FragmentMainPageBinding
import com.abrebo.food_order_app.ui.adapter.FoodsAdapter
import com.abrebo.food_order_app.ui.viewmodel.MainPageViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageFragment : Fragment() {
    private lateinit var binding:FragmentMainPageBinding
    private lateinit var viewModel:MainPageViewModel
    private lateinit var foodList: List<Foods>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp:MainPageViewModel by viewModels()
        viewModel=temp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentMainPageBinding.inflate(inflater, container, false)
        foodList=ArrayList<Foods>()


        viewModel.foodList.observe(viewLifecycleOwner){
            foodList=it
            val adapter=FoodsAdapter(requireContext(),it)
            binding.recyclerView.adapter=adapter
        }

        binding.materialToolbar.setOnMenuItemClickListener { item ->
            val id = item.itemId
            if (id == R.id.app_bar_search) {
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

                return@setOnMenuItemClickListener true
            }else if (id==R.id.address){
                Snackbar.make(binding.materialToolbar,"Ev adresi",Snackbar.LENGTH_SHORT).show()
            }
            false
        }

        return binding.root
    }

}