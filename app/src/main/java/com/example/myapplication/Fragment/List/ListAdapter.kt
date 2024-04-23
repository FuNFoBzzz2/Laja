package com.example.myapplication.Fragment.List

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DB.Recipe
import com.example.myapplication.R

class ListAdapter(private var recipeList: List<Recipe>) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txtname)
        val txtDescription: TextView = itemView.findViewById(R.id.txtdiscription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_row , parent, false)

        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return recipeList.size
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = recipeList[position]
        holder.itemView.findViewById<TextView>(R.id.txtname).setText(currentItem.RecipeName)
        holder.itemView.findViewById<TextView>(R.id.txtdiscription).setText(currentItem.Discription)
    }
    fun setData(recipe: List<Recipe>) {
        this.recipeList = recipe
        notifyDataSetChanged()
    }
}