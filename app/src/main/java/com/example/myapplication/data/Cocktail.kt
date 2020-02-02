package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cocktails")
data class Cocktail (
    @PrimaryKey(autoGenerate = true)
    val cocktailId: Int,
    val idDrink: String?,
    val strDrink: String?
) : Serializable