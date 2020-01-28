package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.adapter.CocktailsAdapter
import com.example.myapplication.data.Cocktail
import com.example.myapplication.viewmodel.CocktailsListViewmodel

class CocktailsListActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private lateinit var viewModel: CocktailsListViewmodel

    private val cocktailsInSearch = ArrayList<Cocktail>()

    private val cocktailsListView: ListView by lazy { findViewById<ListView>(R.id.cocktailsListView) }
    private val noResultText: TextView by lazy { findViewById<TextView>(R.id.noResultText) }

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

        if (viewModel.cocktailsList.value != null) {
            for (cocktail: Cocktail in viewModel.cocktailsList.value!!.iterator()) {
                if (viewModel.search.value != null && viewModel.search.value != "") {
                    if(cocktail.name!!.contains(viewModel.search.value!!, true)) {
                        cocktailsInSearch.add(cocktail)
                    }
                } else {
                    cocktailsInSearch.add(cocktail)
                }
            }
        }

        if (cocktailsInSearch.size > 1) {
            cocktailsListView.adapter = CocktailsAdapter(this, cocktailsInSearch)
            cocktailsListView.onItemClickListener = this
        } else if (cocktailsInSearch.size == 1) {
            goToCocktailDetails(0)
            finish()
        } else {
            noResultText.visibility = View.VISIBLE
        }
    }

    private fun goToCocktailDetails(position: Int) {
        val intent = Intent(this, CocktailsDetailsActivity::class.java)
        intent.putExtra("Cocktail", cocktailsInSearch.get(position))
        startActivity(intent)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        goToCocktailDetails(position)
    }
}
