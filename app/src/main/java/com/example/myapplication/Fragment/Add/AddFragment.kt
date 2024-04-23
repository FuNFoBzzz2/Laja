package com.example.myapplication.Fragment.Add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.model.Recipe
import com.example.myapplication.viewmodel.RecipeViewModel
import com.example.myapplication.R

class AddFragment : Fragment() {

    private lateinit var RViewModel: RecipeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        RViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        view.findViewById<Button>(R.id.buttonadd).setOnClickListener{
            insertDataToDataBase()
        }
        return view
    }

    private fun insertDataToDataBase() {
        val Name = view?.findViewById<EditText>(R.id.txtname)?.text.toString()
        val Discription = view?.findViewById<EditText>(R.id.txtdiscription)?.text.toString()
        if(inputCheck(Name,Discription)){
            val recipe = Recipe(0, Name, Discription)
            RViewModel.addRecipe(recipe)
            Toast.makeText(requireContext(),"Добавление прошло успешно",Toast.LENGTH_LONG ).show()
        findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(name: String, discription: String): Boolean {
        return !(TextUtils.isEmpty(name) &&  TextUtils.isEmpty(discription))
    }
}