package pl.mrz.umpa.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class StationConfig(

    @param:JsonProperty("maxRainInDay")
    @get:JsonProperty("maxRainInDay")
    var maxRainInDay: Float,

    @param:JsonProperty("zones")
    @get:JsonProperty("zones")
    val zones: List<ZoneConfig>
) : Serializable