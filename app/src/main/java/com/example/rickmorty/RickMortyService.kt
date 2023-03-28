package com.example.rickmorty

import okhttp3.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface RickMortyService {
    @GET("character/{character-id}")
    suspend fun getCharacterById(@Path("character-id") characterId: Int
    ):retrofit2.Response<ApiCharacterResponse>
}