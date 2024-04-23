package com.example.myapplication.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.DB.Dao.RecipeDao
import com.example.myapplication.model.Recipe

@Database(
    entities = [Recipe::class],
    version = 1,
    exportSchema = false
)
abstract class RecipeDataBase: RoomDatabase() {
    abstract fun RecipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDataBase? = null

        fun getDatabase(context: Context): RecipeDataBase {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDataBase::class.java,
                    "Clothes_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}