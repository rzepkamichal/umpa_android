package pl.mrz.umpa.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class Rain(
    @param:JsonProperty("rainToday")
    @get:JsonProperty("rainToday")
    val rainToday: Float
) : Serializable