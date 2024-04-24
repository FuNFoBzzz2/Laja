package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapplication.Fragment.List.ListAdapter
import com.example.myapplication.Fragment.List.ListFragment
import com.example.myapplication.viewmodel.RecipeViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            //setupActionBarWithNavController(findNavController(R.id.fragment)

    }
}