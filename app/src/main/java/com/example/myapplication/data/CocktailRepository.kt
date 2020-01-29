package com.example.myapplication.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.COCKTAILS_WEB_SERVICE_URL
import com.example.myapplication.R
import com.example.myapplication.utilities.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class CocktailRepository (val app: Application) {

    val cocktailsList = MutableLiveData<List<Cocktail>>()

    private val listType = Types.newParameterizedType(
        List::class.java, Cocktail::class.java
    )

    init {
        CoroutineScope(Dispatchers.IO).launch {
            getCocktailData()
        }
    }

    @WorkerThread
    suspend fun getCocktailData() {
        if (networkAvailable()) {

            val converterFactory = MoshiConverterFactory.create()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(converterFactory)
                .baseUrl(COCKTAILS_WEB_SERVICE_URL)
                .build()
            val service = retrofit.create(CocktailService::class.java)
            val serviceData = service.getCocktailData().body()

            cocktailsList.postValue(serviceData?.drinks ?: emptyList())
        }
    }

    private fun networkAvailable(): Boolean {
        val connectivityManage = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManage.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }
}