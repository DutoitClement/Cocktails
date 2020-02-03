package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.viewmodel.CocktailsListViewmodel
import kotlinx.android.synthetic.main.activity_search.*

//Activité permettant d'effectuer une recherche de cocktails
class SearchActivity : AppCompatActivity(), View.OnClickListener {

    //Elements de l'UI
    private val searchButton: Button by lazy { findViewById<Button>(R.id.searchButton) }
    private val discoverAllButton: Button by lazy { findViewById<Button>(R.id.discoverAllButton) }
    private val searchEditText: EditText by lazy { findViewById<EditText>(R.id.searchEditText) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        configureUiElements()
    }

    //Configure les éléments de l'UI
    private fun configureUiElements() {
        searchButton.setOnClickListener(this)
        discoverAllButton.setOnClickListener(this)
    }

    //Se rend à la page de liste de cocktails, passe un élément search via l'Intent
    // si l'utilisateur a appuyé sur le bouton search
    private fun goToCocktailsList(search: String?) {

        val cocktailsListIntent = Intent(this, CocktailsListActivity::class.java)

        if (search != null && search.replace(" ", "") != "") {
            cocktailsListIntent.putExtra("Search", search)
        }

        startActivity(cocktailsListIntent)
    }

    //Gère les évènements OnClick des 2 boutons
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.searchButton -> goToCocktailsList(searchEditText.text.toString())
            R.id.discoverAllButton -> goToCocktailsList(null)
        }
    }
}
