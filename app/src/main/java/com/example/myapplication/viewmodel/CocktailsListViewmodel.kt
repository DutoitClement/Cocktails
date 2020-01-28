package com.example.myapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.R
import com.example.myapplication.data.Cocktail
import com.example.myapplication.utilities.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class CocktailsListViewmodel(app: Application) : AndroidViewModel(app) {

    private val listType = Types.newParameterizedType(
        List::class.java, Cocktail::class.java
    )

    val search = MutableLiveData<String>()

    val cocktailsList = MutableLiveData<List<Cocktail>>()

    init {
        val text = FileHelper.getTextFromResources(app, R.raw.cocktails)

        parseText(text)
    }

    fun parseText(text: String) {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter: JsonAdapter<List<Cocktail>> = moshi.adapter(listType)
        cocktailsList.value = adapter.fromJson(text)
    }
}