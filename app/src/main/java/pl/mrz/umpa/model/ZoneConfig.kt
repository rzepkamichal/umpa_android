package pl.mrz.umpa.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ZoneConfig (

    @param:JsonProperty("id")
    @get:JsonProperty("id")
    val id: Int,

    @param:JsonProperty("forceOpenValve")
    @get:JsonProperty("forceOpenValve")
    val forceOpenValve: Int,

    @param:JsonProperty("wateringPreset")
    @get:JsonProperty("wateringPreset")
    val wateringPreset: Int,

    @param:JsonProperty("intervals")
    @get:JsonProperty("intervals")
    val intervals: List<IntervalConfig>
)