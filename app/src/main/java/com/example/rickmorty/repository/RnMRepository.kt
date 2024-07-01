package com.example.rickmorty.repository

import com.example.rickmorty.data.CharacterInfo
import com.example.rickmorty.data.CharacterList
import com.example.rickmorty.data.EpisodeInfo
import com.example.rickmorty.data.EpisodeList
import com.example.rickmorty.api.services.RnMService
import com.example.rickmorty.data.LocationInfo
import retrofit2.Response

class RnMRepository(private val rnmService: RnMService) {

    suspend fun getSingleEpisode(id: Int): Response<EpisodeInfo> {
        return rnmService.getSingleEpisode(id)
    }

    suspend fun getEpisodeBySearch(p0: String): Response<EpisodeList> {
        return rnmService.getEpisodeBySearch(p0)
    }


    suspend fun fetchEpisodes(range: List<Int>): Response<List<EpisodeInfo>> {
        return rnmService.fetchEpisodes(range)

    }

    suspend fun filterCharacterByNameAndGender(gender: String, name: String): Response<CharacterList> {
        return rnmService.filterCharacterByNameAndGender(gender, name)
    }

    suspend fun getCharacter(id: Int): Response<CharacterInfo> {
        return rnmService.getCharacter(id)
    }

    suspend fun getCharacterLocation(characterId: Int): Response<LocationInfo> {
        return rnmService.getCharacterLocation(characterId)
    }
}