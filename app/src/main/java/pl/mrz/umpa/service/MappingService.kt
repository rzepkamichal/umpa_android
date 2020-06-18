package pl.mrz.umpa.service

import pl.mrz.umpa.model.Rain
import pl.mrz.umpa.model.StationConfig
import pl.mrz.umpa.model.ZoneConfig
import pl.mrz.umpa.util.JsonMapper

object MappingService {
    val stationConfigJsonMapper = JsonMapper(StationConfig::class.java)
    val rainJsonMapper = JsonMapper(Rain::class.java)
    val zoneConfigJsonMapper = JsonMapper(ZoneConfig::class.java)
}