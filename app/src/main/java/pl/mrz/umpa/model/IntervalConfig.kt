package pl.mrz.umpa.model

data class IntervalConfig(
    val id: Int,
    val forceOpenValve: Int,
    val openValveHour: Int,
    val openValveMinute: Int,
    val closeValveHour: Int,
    val closeValveMinute: Int,
    val sunday: Int,
    val monday: Int,
    val tuesday: Int,
    val wednesday: Int,
    val thursday: Int,
    val friday: Int,
    val saturday: Int
)