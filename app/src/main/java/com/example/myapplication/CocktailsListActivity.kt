package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.CocktailsAdapter
import com.example.myapplication.data.Cocktail
import com.example.myapplication.viewmodel.CocktailsListViewmodel

class CocktailsListActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private lateinit var viewModel: CocktailsListViewmodel

    private val cocktailsInSearch = ArrayList<Cocktail>()

    private val cocktailsRecyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.cocktailsRecyclerView) }
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val noResultText: TextView by lazy { findViewById<TextView>(R.id.noResultText) }
    private val progressBar: ProgressBar by lazy { findViewById<ProgressBar>(R.id.progressBar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktails_list_navigation_layout)

        viewModel = ViewModelProviders.of(this).get(CocktailsListViewmodel::class.java)

        viewManager = LinearLayoutManager(this)

        cocktailsRecyclerView.setHasFixedSize(true)
        cocktailsRecyclerView.layoutManager = viewManager

        if (intent.hasExtra("Search")) {
            viewModel.search.value = intent.getStringExtra("Search")
        }

        viewModel.cocktailsList.observe(this, Observer {

            progressBar.visibility = View.GONE

            updateCocktailsList()
        })
    }

    private fun updateCocktailsList() {

        if (viewModel.cocktailsList.value != null) {
            for (cocktail: Cocktail in viewModel.cocktailsList.value!!.iterator()) {
                if (viewModel.search.value != null && viewModel.search.value != "") {
                    if(cocktail.strDrink!!.contains(viewModel.search.value!!, true)) {
                        cocktailsInSearch.add(cocktail)
                    }
                } else {
                    cocktailsInSearch.add(cocktail)
                }
            }
        }

        if (cocktailsInSearch.size > 1) {
            cocktailsRecyclerView.adapter = CocktailsAdapter(this, cocktailsInSearch)
            //cocktailsRecyclerView.onItemClickListener = this
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
