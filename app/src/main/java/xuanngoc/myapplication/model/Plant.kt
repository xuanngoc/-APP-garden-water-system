package xuanngoc.myapplication.model


data class Plant (
    val id: Int = 0,
    var name: String = "",
    val minHumidity: Double = 0.0,
    val maxHumidity: Double = 0.0,
    val minTemperature: Double = 0.0,
    val maxTemperature: Double = 0.0
)