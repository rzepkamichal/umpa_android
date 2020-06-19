package pl.mrz.umpa.model

import com.fasterxml.jackson.annotation.JsonProperty
import pl.mrz.umpa.ui.IntervalViewModel
import java.io.Serializable
import java.util.*

data class StationConfig(

    @param:JsonProperty("maxRainInDay")
    @get:JsonProperty("maxRainInDay")
    var maxRainInDay: Float = 0f,

    @param:JsonProperty("zones")
    @get:JsonProperty("zones")
    val zones: List<ZoneConfig> = LinkedList<ZoneConfig>()
) : Serializable {

    fun update(stationConfig: StationConfig){
        maxRainInDay = stationConfig.maxRainInDay
        (zones as LinkedList).clear()
        zones.addAll(stationConfig.zones)
    }
    fun updateInterval(interval: IntervalViewModel, zoneId: Int, intervalId: Int) {
        zones
            .find { it.id == zoneId }
            ?.intervals
            ?.find { it.id == intervalId }
            ?.let {
                it.openValveHour = interval.openValveHour.value ?: 0
                it.openValveMinute = interval.openValveMinute.value ?: 0
                it.closeValveHour = interval.closeValveHour.value ?: 0
                it.closeValveMinute = interval.closeValveMinute.value ?: 0
                it.sunday = interval.sunday.value ?: 0
                it.monday = interval.monday.value ?: 0
                it.tuesday = interval.tuesday.value ?: 0
                it.wednesday = interval.wednesday.value ?: 0
                it.thursday = interval.thursday.value ?: 0
                it.friday = interval.friday.value ?: 0
                it.saturday = interval.saturday.value ?: 0
            }
    }
}