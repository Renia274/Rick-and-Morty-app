package com.example.rickmorty

// This class is a repository for character data that is shared between the ViewModel and data source
class SharedRepository {

    // This function gets the character data for a given character ID from the data source.
    suspend fun getCharacterById(characterId: Int): ApiCharacterResponse? {
        //  network request for the character data
        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        // If the network request is successful, return the character data. Otherwise, return null.
        if (request.isSuccessful) {
            return request.body()
        }

        // If the network request is not successful, return an error message
        return ApiCharacterResponse(
            status = "Error",
            message = "Failed to get character data for character ID $characterId. Please try again later."
        )

    }
}
