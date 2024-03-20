package com.example.rickmorty.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkLayer {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}