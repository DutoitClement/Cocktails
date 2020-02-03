package com.example.myapplication.data

//Objet Drinks, contient une liste de cocktails (on a besoin de cet objet car le JSON renvoyé
// par l'API ne contient pas directement une liste de cocktails mais
// un objet Drinks contenant une liste de cocktails)
data class Drinks(
    val drinks: List<Cocktail>
)