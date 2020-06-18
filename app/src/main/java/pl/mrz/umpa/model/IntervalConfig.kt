package pl.mrz.umpa.model

import com.fasterxml.jackson.annotation.JsonProperty

data class IntervalConfig(
    @param:JsonProperty("id")
    @get:JsonProperty("id")
    val id: Int,

    @param:JsonProperty("openValveHour")
    @get:JsonProperty("openValveHour")
    val openValveHour: Int,

    @param:JsonProperty("openValveMinute")
    @get:JsonProperty("openValveMinute")
    val openValveMinute: Int,

    @param:JsonProperty("closeValveHour")
    @get:JsonProperty("closeValveHour")
    val closeValveHour: Int,

    @param:JsonProperty("closeValveMinute")
    @get:JsonProperty("closeValveMinute")
    val closeValveMinute: Int,

    @param:JsonProperty("sunday")
    @get:JsonProperty("sunday")
    val sunday: Int,

    @param:JsonProperty("monday")
    @get:JsonProperty("monday")
    val monday: Int,

    @param:JsonProperty("tuesday")
    @get:JsonProperty("tuesday")
    val tuesday: Int,

    @param:JsonProperty("wednesday")
    @get:JsonProperty("wednesday")
    val wednesday: Int,

    @param:JsonProperty("thursday")
    @get:JsonProperty("thursday")
    val thursday: Int,

    @param:JsonProperty("friday")
    @get:JsonProperty("friday")
    val friday: Int,

    @param:JsonProperty("saturday")
    @get:JsonProperty("saturday")
    val saturday: Int
)