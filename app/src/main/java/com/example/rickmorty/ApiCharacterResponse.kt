package com.example.rickmorty

import com.squareup.moshi.Json

// This data class represents a response from the Rick and Morty API
data class ApiCharacterResponse(

    // The URL of the character's image
    @Json(name = "image") val image: String? = null,

    @Json(name = "gender") val gender: String? = null,

    @Json(name = "species") val species: String? = null,

    // The date and time when the character was created
    @Json(name = "created") val created: String? = null,

    // The character's place of origin
    @Json(name = "origin") val origin: Origin? = null,

    // The name of the character
    @Json(name = "name") val name: String? = null,

    // The ID of the character
    @Json(name = "id") val id: Int? = null,

    // The type of the character
    @Json(name = "type") val type: String? = null,

    // The URL of the Rick and Morty API endpoint that provides information about the character
    @Json(name = "url") val url: String? = null,

    // The status of the character,for example alive, dead, unknown
    @Json(name = "status") val status: String? = null,

    //error message for unsuccessful request
    val message: String? = null
)

// This data class represents the place of origin of a character
data class Origin(

    // The name of the place of origin
    @Json(name = "name") val name: String? = null,

    // The URL of the Rick and Morty API endpoint that provides information about the place of origin
    @Json(name = "url") val url: String? = null
)
