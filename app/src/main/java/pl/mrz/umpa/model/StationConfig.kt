package pl.mrz.umpa.model

import com.fasterxml.jackson.annotation.JsonProperty

data class StationConfig(

    @param:JsonProperty("maxRainInDay")
    @get:JsonProperty("maxRainInDay")
    val maxRainInDay: Float,

    @param:JsonProperty("zones")
    @get:JsonProperty("zones")
    val zones: List<ZoneConfig>
)