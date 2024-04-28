package com.example.myapplication.Fragment.List


import android.os.Bundle

import androidx.fragment.app.Fragment

import android.view.LayoutInflater

import android.view.View

import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SearchView

import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.viewmodel.RecipeViewModel
import com.example.myapplication.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {
    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var adapter: ListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val adapter = ListAdapter(emptyList())
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        recipeViewModel.readAllRecipe.observe(viewLifecycleOwner, Observer { recipe ->
            adapter.setData(recipe)
        })

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        view.findViewById<ImageButton>(R.id.searchViewList).setOnClickListener{findNavController().navigate(R.id.action_listFragment_to_searchFragment)}
        return view
    }
}