package com.example.rickmorty

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// This object contains network-related functionality for the Rick and Morty app
object NetworkLayer {

    // Creation a Moshi object to deserialize JSON to Kotlin objects
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    // Create a Retrofit object with Ricky and Morty API base URL and a MoshiConverterFactory to convert JSON to Kotlin objects
    val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    // Create a lazy initialized RickMortyService object that uses the Retrofit object to make Rick and Morty API requests
    val rickMortyService: RickMortyService by lazy {
        retrofit.create(RickMortyService::class.java)
    }

    // Create an ApiClient object that uses the RickMortyService object to make API requests
    val apiClient = ApiClient(rickMortyService)

}
