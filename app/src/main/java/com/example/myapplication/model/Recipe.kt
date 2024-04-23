package com.example.myapplication.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Recipe")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    var IDRecipe: Int,//? = null,
    var RecipeName: String,
    var Image: String?,
    var Discription: String
): Parcelable