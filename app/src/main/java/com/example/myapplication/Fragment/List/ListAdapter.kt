package com.example.myapplication.Fragment.List

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DB.Recipe
import com.example.myapplication.R

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>(){
    private  var recipelist = emptyList<Recipe>()
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row,parent,false))
    }

    override fun getItemCount(): Int {
        return recipelist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = recipelist[position]
        holder.itemView.findViewById<EditText>(R.id.txtname).setText(currentItem.RecipeName)
        holder.itemView.findViewById<EditText>(R.id.txtdiscription).setText(currentItem.Discription)
    }
    fun setData(recipe: List<Recipe>){
        this.recipelist = recipe
        notifyDataSetChanged()
    }

}