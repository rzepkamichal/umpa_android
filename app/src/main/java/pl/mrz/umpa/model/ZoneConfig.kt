package pl.mrz.umpa.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class ZoneConfig(

    @param:JsonProperty("id")
    @get:JsonProperty("id")
    val id: Int,

    @param:JsonProperty("forceOpenValve")
    @get:JsonProperty("forceOpenValve")
    var forceOpenValve: Int,

    @param:JsonProperty("wateringPreset")
    @get:JsonProperty("wateringPreset")
    var wateringPreset: Int,

    @param:JsonProperty("intervals")
    @get:JsonProperty("intervals")
    val intervals: List<IntervalConfig>
) : Serializable