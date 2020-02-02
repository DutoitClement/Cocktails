package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.Cocktail

class CocktailsAdapter(val context: Context,
                       val cocktails: List<Cocktail>,
                       val onCocktailClickListener: OnCocktailClickListener): RecyclerView.Adapter<CocktailsAdapter.ViewHodler>() {

    inner class ViewHodler(itemView: View): RecyclerView.ViewHolder(itemView) {
        val picture: ImageView = itemView.findViewById(R.id.picture)
        val name: TextView = itemView.findViewById(R.id.name)
    }

    override fun getItemCount() = cocktails.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHodler {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.cocktail_list_item, parent, false)
        return ViewHodler(view)
    }

    override fun onBindViewHolder(holder: ViewHodler, position: Int) {
        val cocktail = cocktails[position]
        with(holder) {
            picture.background = context.resources.getDrawable(R.color.black)
            name.text = cocktail.strDrink
        }

        holder.itemView.setOnClickListener { onCocktailClickListener.onCocktailClick(position) }
    }

    interface OnCocktailClickListener {
        fun onCocktailClick(position: Int)
    }
}