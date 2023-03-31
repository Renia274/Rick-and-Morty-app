package com.example.rickmorty


import retrofit2.http.GET
import retrofit2.http.Path


interface RickMortyService {

    //  makes a GET request to the Rick and Morty API endpoint for a specific character based on its id
    @GET("character/{character-id}")
    suspend fun getCharacterById(
        // Specify the character ID as a path
        @Path("character-id") characterId: Int
    ): retrofit2.Response<ApiCharacterResponse>
}
