package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.adapter.CocktailsAdapter
import com.example.myapplication.model.Cocktail
import com.example.myapplication.viewmodel.CocktailsListViewmodel
import kotlinx.android.synthetic.main.activity_cocktails_list.*
import java.util.ArrayList

class CocktailsListActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private lateinit var viewModel: CocktailsListViewmodel

    private val cocktailsListView: ListView by lazy { findViewById<ListView>(R.id.cocktailsListView) }
    private val noResultText: TextView by lazy { findViewById<TextView>(R.id.noResultText) }

    private var cocktailsList = ArrayList<Cocktail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktails_list_navigation_layout)

        viewModel = ViewModelProviders.of(this).get(CocktailsListViewmodel::class.java)

        if (intent.hasExtra("Search")) {
            viewModel.search.value = intent.getStringExtra("Search")
        }

        initUiElements()
    }

    private fun initUiElements() {

        val cocktails = ArrayList<Cocktail>()

        cocktails.add(Cocktail("Margarita"))
        cocktails.add(Cocktail("Sex on the beach"))
        cocktails.add(Cocktail("Pinacolada"))
        cocktails.add(Cocktail("Panama"))
        cocktails.add(Cocktail("Curacao punch"))
        cocktails.add(Cocktail("Paradise"))

        for (cocktail: Cocktail in cocktails) {
            if (viewModel.search.value != null && viewModel.search.value != "") {
                if(cocktail.name.contains(viewModel.search.value!!, true)) {
                    cocktailsList.add(cocktail)
                }
            } else {
                cocktailsList.add(cocktail)
            }
        }

        if (cocktailsList.size > 1) {
            cocktailsListView.adapter = CocktailsAdapter(this, cocktailsList)
            cocktailsListView.onItemClickListener = this
        } else if (cocktailsList.size == 1) {
            goToCocktailDetails(0)
            finish()
        } else {
            noResultText.visibility = View.VISIBLE
        }
    }

    private fun goToCocktailDetails(position: Int) {
        val intent = Intent(this, CocktailsDetailsActivity::class.java)
        intent.putExtra("Cocktail", cocktailsList.get(position))
        startActivity(intent)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        goToCocktailDetails(position)
    }
}
