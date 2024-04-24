package com.example.myapplication.DB.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRecipe(Recipe: Recipe)

    @Update
    suspend fun updateRecipe(Recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("DELETE FROM Recipe")
    suspend fun deleteALLRecipe()
    @Query("SELECT * FROM Recipe WHERE RecipeName LIKE :query")
    fun searchrecipe(query: String): Flow<List<Recipe>>

    @Query("SELECT COUNT(*) FROM Recipe WHERE Image = :imagePath")
    fun isImage(imagePath: String?): Boolean
    @Query("Select * from Recipe ORDER BY IDRecipe ASC")
    fun readAllRecipe(): LiveData<List<Recipe>>

}