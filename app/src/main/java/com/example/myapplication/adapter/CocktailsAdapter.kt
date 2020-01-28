package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.model.Cocktail
import java.util.*

class CocktailsAdapter(private val context: Context, private val cocktailsList: ArrayList<Cocktail>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val itemView = inflater.inflate(R.layout.cocktail_list_item, parent, false)

        val picture: ImageView = itemView.findViewById(R.id.picture)
        val name: TextView = itemView.findViewById(R.id.name)

        picture.background = context.resources.getDrawable(R.color.black)
        name.text = cocktailsList.get(position).name

        return itemView
    }

    override fun getItem(position: Int): Any {
        return cocktailsList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return cocktailsList.size
    }
}