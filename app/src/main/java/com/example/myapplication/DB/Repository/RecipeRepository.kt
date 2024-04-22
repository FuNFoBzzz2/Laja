package com.example.myapplication.DB.Repository

import androidx.lifecycle.LiveData
import com.example.myapplication.DB.Dao.RecipeDao
import com.example.myapplication.DB.Recipe

class RecipeRepository(private val recipeDao: RecipeDao) {
    val readAllRecipe: LiveData<List<Recipe>> = recipeDao.readAllRecipe()
    suspend fun insertRecipe(recipe: Recipe) {
        recipeDao.addRecipe(recipe)
    }
}