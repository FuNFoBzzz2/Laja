package com.example.myapplication.Fragment.Search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.query
import com.example.myapplication.R
import com.example.myapplication.viewmodel.RecipeViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SearchFragment : Fragment() {
    private lateinit var RViewModel: RecipeViewModel
    private lateinit var adapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        //ViewModel
        RViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        //RecyclerView
        adapter = SearchAdapter(RViewModel)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        // Установка фокуса на EditText для начала ввода поискового запроса
        val searchInput = view.findViewById<EditText>(R.id.editTextText)
        searchInput.requestFocus()
        // Обновление списка при изменении текста в EditText\
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Ничего не делаем
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Ничего не делаем
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                updateAdapter(searchText)
            }
        })
        return view
    }

    private fun updateAdapter(searchText: String) {
        RViewModel.readAllRecipe.observe(viewLifecycleOwner, Observer { recipe ->
            val filteredList = recipe.filter { item ->
                item.RecipeName.contains(searchText, ignoreCase = true) ||
                        item.Discription.contains(searchText, ignoreCase = true)
            }
            adapter.setData(filteredList)
        })
    }
}
