package com.example.rickmorty.api.services

import com.example.rickmorty.data.CharacterInfo
import com.example.rickmorty.data.CharacterList
import com.example.rickmorty.data.EpisodeInfo
import com.example.rickmorty.data.EpisodeList
import com.example.rickmorty.data.LocationInfo
import com.example.rickmorty.data.LocationList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RnMService {

    @GET("episode/{id}")
    suspend fun getSingleEpisode(@Path("id") id: Int): Response<EpisodeInfo>


    @GET("episode")
    suspend fun getEpisodeBySearch(@Query("name") p0: String): Response<EpisodeList>


    @GET("episode/{ids}")
    suspend fun fetchEpisodes(@Path("ids") range: List<Int>): Response<List<EpisodeInfo>>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<CharacterInfo>


    @GET("character")
    suspend fun filterCharacterByNameAndGender(
        @Query("gender") gender: String,
        @Query("name") name: String
    ): Response<CharacterList>


    @GET("location/{id}")
    suspend fun getCharacterLocation(@Path("id") id: Int): Response<LocationInfo>
}