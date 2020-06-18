package pl.mrz.umpa.model

data class StationConfig(
    val maxRainInDay: Float,
    val zones: Array<ZoneConfig>
)