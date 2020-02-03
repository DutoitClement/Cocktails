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

//Activité affichant une liste de cocktails
class CocktailsListActivity : AppCompatActivity(), CocktailsAdapter.OnCocktailClickListener {

    //Eléments de l'UI
    private lateinit var viewModel: CocktailsListViewmodel

    private val cocktailsSwipeRefreshLayout: SwipeRefreshLayout by lazy { findViewById<SwipeRefreshLayout>(R.id.cocktailsSwipeRefreshLayout) }
    private val cocktailsRecyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.cocktailsRecyclerView) }
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val noResultText: TextView by lazy { findViewById<TextView>(R.id.noResultText) }
    private val progressBar: ProgressBar by lazy { findViewById<ProgressBar>(R.id.progressBar) }

    //Recherche passée par l'activité précédente via l'Intent
    private var search: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktails_list_navigation_layout)

        //On récupère l'instance du ViewModel
        viewModel = ViewModelProviders.of(this).get(CocktailsListViewmodel::class.java)

        //Si l'Intent contient une recherche, on la stocke dans la variable "search"
        if (intent.hasExtra("Search")) {
            search = intent.getStringExtra("Search")
        }

        //Initialise les éléments de l'UI
        initUiElements()

        //Rafraichit la liste de cocktails du viewmodel
        viewModel.refreshCocktailsData(search)
    }

    //Initialise les éléments de l'UI
    private fun initUiElements() {
        viewManager = LinearLayoutManager(this)
        cocktailsRecyclerView.setHasFixedSize(true)
        cocktailsRecyclerView.layoutManager = viewManager

        cocktailsSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshCocktailsData(search)
        }

        //La méthode observe permet d'exécuter du code chaque fois qu'une modification est apportée
        // à un objet MutableLiveData d'un ViewModel
        //Ici on rafraichit la liste de cocktails
        viewModel.cocktailsList.observe(this, Observer {
            progressBar.visibility = View.GONE
            cocktailsSwipeRefreshLayout.isRefreshing = false
            updateCocktailsList()
        })
    }

    //Update la liste de cocktails
    private fun updateCocktailsList() {

        if (viewModel.cocktailsList.value != null) {

            //Si plus d'un cocktail est retourné par l'API, on les affiche en liste dan sle RecyclerView
            //Si un seul élément est renvoyé, on passe directement à l'activité Details
            //Si aucun élément n'est renvoyé, on affiche un texte indiquant à l'utilisateur que sa recherche n'a pas retourné de résultats
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

    //Lorsque l'utolisateur clique sur un cocktail dans le RecyclerView, on affiche les détails du cocktail
    override fun onCocktailClick(position: Int) {
        goToCocktailDetails(position)
    }
}
