package com.example.myapplication.DB.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.DB.Recipe

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRecipe(Recipe: Recipe)

    @Query("Select * from Recipe ORDER BY IDRecipe ASC")
    fun readAllRecipe(): LiveData<List<Recipe>>

}