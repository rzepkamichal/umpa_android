package pl.mrz.umpa.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class IntervalConfig(
    @param:JsonProperty("id")
    @get:JsonProperty("id")
    val id: Int,

    @param:JsonProperty("openValveHour")
    @get:JsonProperty("openValveHour")
    var openValveHour: Int,

    @param:JsonProperty("openValveMinute")
    @get:JsonProperty("openValveMinute")
    var openValveMinute: Int,

    @param:JsonProperty("closeValveHour")
    @get:JsonProperty("closeValveHour")
    var closeValveHour: Int,

    @param:JsonProperty("closeValveMinute")
    @get:JsonProperty("closeValveMinute")
    var closeValveMinute: Int,

    @param:JsonProperty("sunday")
    @get:JsonProperty("sunday")
    var sunday: Int,

    @param:JsonProperty("monday")
    @get:JsonProperty("monday")
    var monday: Int,

    @param:JsonProperty("tuesday")
    @get:JsonProperty("tuesday")
    var tuesday: Int,

    @param:JsonProperty("wednesday")
    @get:JsonProperty("wednesday")
    var wednesday: Int,

    @param:JsonProperty("thursday")
    @get:JsonProperty("thursday")
    var thursday: Int,

    @param:JsonProperty("friday")
    @get:JsonProperty("friday")
    var friday: Int,

    @param:JsonProperty("saturday")
    @get:JsonProperty("saturday")
    var saturday: Int
) : Serializable