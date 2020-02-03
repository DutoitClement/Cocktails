package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.Cocktail
import com.example.myapplication.data.CocktailRepository

//ViewModel, contient toutes les méthodes d'accès aux données de l'activité CocktailsListActivity
class CocktailsListViewmodel(app: Application) : AndroidViewModel(app) {

    private val dataRepository = CocktailRepository(app)

    val cocktailsList = dataRepository.cocktailsList

    fun refreshCocktailsData(search: String?) {
        dataRepository.refreshCocktailsData(search)
    }
}