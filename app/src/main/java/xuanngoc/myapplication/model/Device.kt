package xuanngoc.myapplication.model

import com.squareup.moshi.Json

data class Device(
    val id: Int = 0,

    @Json(name = "device_type_id")
    val deviceTypeId: Int = 0,

    @Json(name = "garden_id")
    val gardenId: Int = 0,
    val name: String = "",
    val status: String = "",
    val state: Boolean = false,
    val isManual: Boolean = false
)