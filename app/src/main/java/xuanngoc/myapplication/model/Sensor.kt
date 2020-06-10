package xuanngoc.myapplication.model

import com.squareup.moshi.Json

data class Sensor(
    val id: Int = 0,

    @Json(name = "sensor_type_id")
    val sensorTypeId: Int = 0,

    @Json(name = "garden_id")
    val gardenId: Int = 0,
    val name: String = "",
    val status: String = "",
    val state: Boolean = false
)