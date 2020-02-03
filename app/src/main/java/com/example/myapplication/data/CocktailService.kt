package com.example.myapplication.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//Contient toutes les méthodes d'appel à l'API cocktails
interface CocktailService {
    @GET("json/v1/1/search.php")
    suspend fun getCocktailsByFirstName(@Query("f") firstLetter: String): Response<Drinks>

    @GET("json/v1/1/search.php")
    suspend fun getCocktailsBySearch(@Query("s") search: String): Response<Drinks>
}