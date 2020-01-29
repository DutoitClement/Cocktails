package com.example.myapplication.data

import retrofit2.Response
import retrofit2.http.GET

interface CocktailService {
    @GET("search.php?f=a")
    suspend fun getCocktailData(): Response<List<Cocktail>>
}