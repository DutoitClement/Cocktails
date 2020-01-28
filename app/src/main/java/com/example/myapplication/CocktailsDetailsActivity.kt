package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.model.Cocktail

class CocktailsDetailsActivity : AppCompatActivity() {

    private val picture: ImageView by lazy { findViewById<ImageView>(R.id.picture) }
    private val title: TextView by lazy { findViewById<TextView>(R.id.name) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktails_details)

        if (intent.hasExtra("Cocktail")) {

            val cocktail: Cocktail = intent.getSerializableExtra("Cocktail") as Cocktail

            picture.background = resources.getDrawable(R.color.black)
            title.text = cocktail.name
        }
    }
}
