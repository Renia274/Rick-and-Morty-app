package com.example.rickmorty

import okhttp3.Response
import java.sql.RowId

class ApiClient(private val rickMortyService:RickMortyService) {

    suspend fun getCharacterById(characterId: Int): retrofit2.Response<ApiCharacterResponse> {
        return rickMortyService.getCharacterById(characterId)
    }
}