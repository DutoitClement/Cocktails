package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.adapter.CocktailsAdapter
import com.example.myapplication.viewmodel.CocktailsListViewmodel

class CocktailsListActivity : AppCompatActivity(), CocktailsAdapter.OnCocktailClickListener {

    private lateinit var viewModel: CocktailsListViewmodel

    private val cocktailsSwipeRefreshLayout: SwipeRefreshLayout by lazy { findViewById<SwipeRefreshLayout>(R.id.cocktailsSwipeRefreshLayout) }
    private val cocktailsRecyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.cocktailsRecyclerView) }
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val noResultText: TextView by lazy { findViewById<TextView>(R.id.noResultText) }
    private val progressBar: ProgressBar by lazy { findViewById<ProgressBar>(R.id.progressBar) }

    private var search: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktails_list_navigation_layout)

        viewModel = ViewModelProviders.of(this).get(CocktailsListViewmodel::class.java)

        if (intent.hasExtra("Search")) {
            search = intent.getStringExtra("Search")
        }

        initUiElements()

        viewModel.refreshCocktailsData(search)
    }

    private fun initUiElements() {
        viewManager = LinearLayoutManager(this)
        cocktailsRecyclerView.setHasFixedSize(true)
        cocktailsRecyclerView.layoutManager = viewManager

        cocktailsSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshCocktailsData(search)
        }

        viewModel.cocktailsList.observe(this, Observer {
            progressBar.visibility = View.GONE
            cocktailsSwipeRefreshLayout.isRefreshing = false
            updateCocktailsList()
        })
    }

    private fun updateCocktailsList() {

        if (viewModel.cocktailsList.value != null) {
            if (viewModel.cocktailsList.value!!.size > 1) {
                cocktailsRecyclerView.adapter = CocktailsAdapter(this, viewModel.cocktailsList.value!!, this)
            } else if (viewModel.cocktailsList.value!!.size == 1) {
                goToCocktailDetails(0)
                finish()
            } else {
                noResultText.visibility = View.VISIBLE
            }
        }
    }

    private fun goToCocktailDetails(position: Int) {
        val intent = Intent(this, CocktailsDetailsActivity::class.java)
        intent.putExtra("Cocktail", viewModel.cocktailsList.value?.get(position))
        startActivity(intent)
    }

    override fun onCocktailClick(position: Int) {
        goToCocktailDetails(position)
    }
}
