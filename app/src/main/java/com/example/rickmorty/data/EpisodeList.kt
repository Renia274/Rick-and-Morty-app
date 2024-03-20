package com.example.rickmorty.data

import com.google.gson.annotations.SerializedName


data class EpisodeList(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<EpisodeInfo>
)