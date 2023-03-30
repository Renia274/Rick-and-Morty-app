package com.example.rickmorty

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val headerImageView = findViewById<ImageView>(R.id.headerImageView)
        val aliveTextView = findViewById<TextView>(R.id.aliveTextView)
        val originTextView = findViewById<TextView>(R.id.OriginTextView)
        val speciesTextView = findViewById<TextView>(R.id.SpeciesTextView)
        val genderImageView = findViewById<ImageView>(R.id.genderImageView)

        viewModel.characterLiveData.observe(this){response->
            if(response==null){

                Toast.makeText(this@MainActivity,"not success call to the network",Toast.LENGTH_SHORT).show()
                return@observe
        }

            nameTextView.text = response?.name
            aliveTextView.text = response?.status
            speciesTextView.text = response?.species
            originTextView.text = response?.origin?.name

            if(response?.gender.equals("male", ignoreCase = true)){
            genderImageView.setImageResource(R.drawable.ic_male_24)
            } else {
                    genderImageView.setImageResource(R.drawable.ic_female_24)
               }

            Picasso.get().load(response?.image).into(headerImageView)

    }
}}