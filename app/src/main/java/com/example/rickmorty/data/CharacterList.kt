package com.example.rickmorty.data

import com.google.gson.annotations.SerializedName

data class CharacterList(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<CharacterInfo>
)
