package pl.mrz.umpa.model

data class ZoneConfig (
    val id: Int,
    val forceOpenValve: Int,
    val wateringPreset: Int,
    val intervals: Array<IntervalConfig>
)