package com.example.myapplication.data

import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CocktailService {
    @GET("json/v1/1/search.php?f=a")
    suspend fun getCocktailData(): Response<Drinks>
}