package com.example.myapplication.DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Recipe")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    var IDRecipe: Int,//? = null,
    var RecipeName: String,
    //var Image: String?,
    var Discription: String
)