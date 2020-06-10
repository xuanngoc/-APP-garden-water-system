package xuanngoc.myapplication.model

import java.util.*

data class SensorValue (
    val id: Int = 0,
    val sensor: Sensor = Sensor(),
    val value: Double = 0.0,
    val dateTime: Date = Date()
)