package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.DB.RecipeDataBase
import com.example.myapplication.repository.RecipeRepository
import com.example.myapplication.model.Recipe
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
    fun updateRecipe(recipe: Recipe){
        viewModelScope.launch(Dispatchers.IO){
            repositoryRecipe.updateRecipe(recipe)
        }
    }
    fun deleteRecipe(recipe: Recipe){
        viewModelScope.launch(Dispatchers.IO){
            repositoryRecipe.deleteRecipe(recipe)
        }
    }
    fun isImage(imagePath: String?): Boolean {
        return repositoryRecipe.isImage(imagePath)
    }
    fun deleteAllRecipe(){
        viewModelScope.launch(Dispatchers.IO){
            repositoryRecipe.deleteAllRecipe()
        }
    }
}