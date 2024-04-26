package com.example.myapplication.Fragment.Search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Recipe
import com.example.myapplication.viewmodel.RecipeViewModel

class SearchAdapter (private var RecipeViewModel: RecipeViewModel) : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {
    private var recipeItem = emptyList<Recipe>()
    private val titleLengthLimit = 35
    private val descriptionLengthLimit = 50
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun getItemCount(): Int {
        return recipeItem.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = recipeItem[position]

        holder.itemView.apply {
            //Title
            val titleTextView = findViewById<TextView>(R.id.txtname)
            textViewLimit(titleTextView, currentItem.RecipeName, titleLengthLimit)
            //Description
            val descriptionTextView = findViewById<TextView>(R.id.txtdiscription)
            textViewLimit(descriptionTextView, currentItem.Discription, descriptionLengthLimit)

            //Передача элемента на окно обновления
            findViewById<ConstraintLayout>(R.id.rowLayout).setOnClickListener {
                val action = SearchFragmentDirections.actionSearchFragmentToUpdateFragment(currentItem)
                findNavController().navigate(action)
            }
        }
    }

    fun setData(clothingItem: List<Recipe>) {
        recipeItem = clothingItem
        notifyDataSetChanged()
    }

    private fun textViewLimit(tv: TextView, text: String, limit: Int) {
        if (text.length > limit) {
            tv.text = text.substring(0, titleLengthLimit) + "..."
        } else {
            tv.text = text
        }
    }
}