package com.example.myapplication.DB

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.DB.Repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    val readAllRecipe: LiveData<List<Recipe>>
    private val repositoryRecipe: RecipeRepository

    init {
        val recipeDao = RecipeDataBase.getDatabase(application).RecipeDao()
        repositoryRecipe = RecipeRepository(recipeDao)
        readAllRecipe = repositoryRecipe.readAllRecipe
    }

    fun addRecipe(Recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryRecipe.insertRecipe(Recipe)
        }
    }
}