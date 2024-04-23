package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.DB.Dao.RecipeDao
import com.example.myapplication.model.Recipe

class RecipeRepository(private val recipeDao: RecipeDao) {
    val readAllRecipe: LiveData<List<Recipe>> = recipeDao.readAllRecipe()
    suspend fun insertRecipe(recipe: Recipe) {
        recipeDao.addRecipe(recipe)
    }
    suspend fun updateRecipe(recipe: Recipe){
        recipeDao.updateRecipe(recipe)
    }
    suspend fun deleteRecipe(recipe: Recipe){
        recipeDao.deleteRecipe(recipe)
    }
    suspend fun deleteAllRecipe(){
        recipeDao.deleteALLRecipe()
    }
}