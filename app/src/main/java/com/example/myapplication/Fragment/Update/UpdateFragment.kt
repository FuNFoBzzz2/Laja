package com.example.myapplication.Fragment.Update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.model.Recipe
import com.example.myapplication.viewmodel.RecipeViewModel
@Suppress("DEPRECATION")
class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var RViewModel: RecipeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_update, container, false)
        RViewModel = ViewModelProvider(this).get(com.example.myapplication.viewmodel.RecipeViewModel::class.java)
        view.findViewById<EditText>(R.id.txtnameupdate).setText(args.currentRecipe.RecipeName)
        view.findViewById<EditText>(R.id.txtdiscriptionupdate).setText(args.currentRecipe.Discription)
        view.findViewById<Button>(R.id.buttondelete).setOnClickListener{
            deleteRecipe()
        }
        view.findViewById<Button>(R.id.buttonaddupdate).setOnClickListener{
            updateItem()
        }
        setHasOptionsMenu(true)
        return view
    }
    private fun updateItem(){
        val RName = view?.findViewById<EditText>(R.id.txtnameupdate)?.text.toString()
        val RDiscription = view?.findViewById<EditText>(R.id.txtdiscriptionupdate)?.text.toString()
        if(inputCheck(RName, RDiscription)){
            val updateRecipe = Recipe(args.currentRecipe.IDRecipe, RName, RDiscription)
            RViewModel.updateRecipe(updateRecipe)
            Toast.makeText(requireContext(), "Обновление прошло успешно!",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Возникла ошибка",Toast.LENGTH_SHORT).show()
        }
    }
    private fun inputCheck(RName: String, RDiscription: String): Boolean {
        return !(TextUtils.isEmpty(RName) &&  TextUtils.isEmpty(RDiscription))
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.invalidateOptionsMenu()
    }
    private fun deleteRecipe() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setNegativeButton("Нет"){_, _ -> }
        builder.setPositiveButton("ДА"){_, _ ->
            RViewModel.deleteRecipe(args.currentRecipe)
            Toast.makeText(requireContext(), "Удаление: ${args.currentRecipe.RecipeName}",Toast.LENGTH_SHORT ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setTitle("Удалить ${args.currentRecipe.RecipeName}")
        builder.setMessage("Вы уверены что хотите удалить ${args.currentRecipe.RecipeName}")
        builder.create().show()
    }
}