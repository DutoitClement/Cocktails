package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Objet RoomDatabase, permet de créer une base de données SQLite via la librairie Room
@Database(entities = [Cocktail::class], version = 1, exportSchema = false)
abstract class CocktailDatabase: RoomDatabase() {

    abstract fun cocktailDao(): CocktailDao

    companion object {

        //Un objet volatile est un objet qui peut être utilisé par plusieurs threads à la foir
        @Volatile
        private var INSTANCE: CocktailDatabase? = null

        fun getDatabase(context: Context): CocktailDatabase {
            if (INSTANCE == null) {

                //Synchronized veut dire que ce code ne peut être utilisé que par un thread à la fois
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CocktailDatabase::class.java,
                        "cocktails.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}