package pl.mrz.umpa.model

import com.fasterxml.jackson.annotation.JsonProperty
import pl.mrz.umpa.ui.IntervalViewModel
import pl.mrz.umpa.ui.StationViewModel
import pl.mrz.umpa.ui.ZoneViewModel
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

    fun updateWithIntervalModel(intervalModel: IntervalViewModel, zoneId: Int, intervalId: Int) {
        zones
            .find { it.id == zoneId }
            ?.intervals
            ?.find { it.id == intervalId }
            ?.let {
                it.openValveHour = intervalModel.openValveHour.value ?: it.openValveHour
                it.openValveMinute = intervalModel.openValveMinute.value ?: it.openValveMinute
                it.closeValveHour = intervalModel.closeValveHour.value ?: it.closeValveHour
                it.closeValveMinute = intervalModel.closeValveMinute.value ?: it.closeValveMinute
                it.sunday = intervalModel.sunday.value ?: it.sunday
                it.monday = intervalModel.monday.value ?: it.monday
                it.tuesday = intervalModel.tuesday.value ?: it.tuesday
                it.wednesday = intervalModel.wednesday.value ?: it.wednesday
                it.thursday = intervalModel.thursday.value ?: it.thursday
                it.friday = intervalModel.friday.value ?: it.friday
                it.saturday = intervalModel.saturday.value ?: it.saturday
            }
    }

    fun updateWithZoneModel(zoneModel: ZoneViewModel, zoneId: Int) {
        zones
            .find { it.id == zoneId }
            ?.apply {
                forceOpenValve = zoneModel.forceOpenValve.value ?: forceOpenValve
                wateringPreset = zoneModel.wateringPreset.value ?: wateringPreset
            }
    }

    fun updateWithStationModel(stationModel: StationViewModel) {
        maxRainInDay = stationModel.maxRainInDay.value ?: maxRainInDay
    }
}