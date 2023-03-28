package com.example.rickmorty

class SharedRepository {

    suspend fun getCharacterById(characterId: Int): ApiCharacterResponse? {
        //call suspend function
        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if (request.isSuccessful) {
            return request.body()
        }

        return null
    }
}
