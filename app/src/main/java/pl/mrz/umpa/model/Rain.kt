package pl.mrz.umpa.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Rain(
    @param:JsonProperty("rainToday")
    @get:JsonProperty("rainToday")
    val rainToday: Float
)