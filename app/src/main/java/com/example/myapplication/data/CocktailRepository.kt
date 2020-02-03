package com.example.myapplication.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.COCKTAILS_WEB_SERVICE_URL
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

//Repository, contient toutes les méthodes d'accès aux données
class CocktailRepository (val app: Application) {

    val cocktailsList = MutableLiveData<List<Cocktail>>()
    private val cocktailDao = CocktailDatabase.getDatabase(app).cocktailDao()

    //Rafraichit la liste de cocktails
    fun refreshCocktailsData(search: String?) {

        //Lance une coroutine (tache asynchrone)
        CoroutineScope(Dispatchers.IO).launch {
            val data = cocktailDao.getAll()

            //Si le réseau est disponible, on récupère les données via l'API,
            //sinon on les récupère via la base de données
            if(networkAvailable()) {
                getCocktailDataFromWebservice(search)
                Log.d("DEBUGDATA", "Using Network")
            } else if (!data.isEmpty()) {
                cocktailsList.postValue(data)
                Log.d("DEBUGDATA", "Using Database")
            }
        }
    }

    //Récupère les datas via l'API Cocktails
    @WorkerThread
    suspend fun getCocktailDataFromWebservice(search: String?) {

        //Le converteur Moshi permet de traiter les données JSON recues par l'API
        val converterFactory = MoshiConverterFactory.create()

        //Retrofit se charge des appels à l'API
        val retrofit = Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .baseUrl(COCKTAILS_WEB_SERVICE_URL)
            .build()
        val service = retrofit.create(CocktailService::class.java)

        val cocktailsData: List<Cocktail>?

        if (search != null) {
            cocktailsData = service.getCocktailsBySearch(search).body()?.drinks
        } else {
            cocktailsData = service.getCocktailsByFirstName("a").body()?.drinks
        }
        cocktailsList.postValue(cocktailsData ?: emptyList())

        if (cocktailsData != null) {
            cocktailDao.deleteAll()
            cocktailDao.insertCocktails(cocktailsData)
        }
    }

    //Check si un accès à Internet est possible
    private fun networkAvailable(): Boolean {
        val connectivityManage = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManage.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }
}