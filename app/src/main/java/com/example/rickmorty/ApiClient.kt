package com.example.rickmorty



class ApiClient(private val rickMortyService:RickMortyService) {

    //makes a call to Rick and Morty API endpoint and returns a response
    suspend fun getCharacterById(characterId: Int): retrofit2.Response<ApiCharacterResponse> {
        return rickMortyService.getCharacterById(characterId)
    }
}