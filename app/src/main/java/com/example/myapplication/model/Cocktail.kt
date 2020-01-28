package com.example.myapplication.model

import java.io.Serializable

class Cocktail (name: String) : Serializable {

    var name: String = name
    var image: String? = null
}