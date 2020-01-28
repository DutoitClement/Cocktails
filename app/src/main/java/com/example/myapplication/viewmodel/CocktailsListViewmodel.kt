package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CocktailsListViewmodel : ViewModel() {

    val search = MutableLiveData<String>()
}