package com.example.rickmorty

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        // Call refreshCharacter() method with the character id you want to display
        viewModel.refreshCharacter(1)

        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val headerImageView = findViewById<ImageView>(R.id.headerImageView)
        val aliveTextView = findViewById<TextView>(R.id.aliveTextView)
        val originTextView = findViewById<TextView>(R.id.OriginTextView)
        val speciesTextView = findViewById<TextView>(R.id.SpeciesTextView)
        val genderImageView = findViewById<ImageView>(R.id.genderImageView)

        viewModel.characterLiveData.observe(this) { response ->
            if (response == null) {
                Toast.makeText(this@MainActivity, "Not able to fetch the data", Toast.LENGTH_SHORT).show()
                return@observe
            }

            // Update the UI views with the character data
            nameTextView.text = response.name
            aliveTextView.text = response.status
            speciesTextView.text = response.species
            originTextView.text = response.origin?.name

            // Set visibility of views to make sure they are visible
            nameTextView.visibility = View.VISIBLE
            aliveTextView.visibility = View.VISIBLE
            speciesTextView.visibility = View.VISIBLE
            originTextView.visibility = View.VISIBLE
            genderImageView.visibility = View.VISIBLE
            headerImageView.visibility = View.VISIBLE

            if (response.gender.equals("male", ignoreCase = true)) {
                genderImageView.setImageResource(R.drawable.ic_male_24)
            } else {
                genderImageView.setImageResource(R.drawable.ic_female_24)
            }

            Picasso.get().load(response.image).into(headerImageView)

        }
    }
}
