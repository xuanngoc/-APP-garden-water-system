package xuanngoc.myapplication.model

import com.squareup.moshi.Json


data class Garden(
    val id: Int = 0,
    var name: String = "",
    val area: Double = 0.0,

    @Json(name = "plant_id")
    val plantId: Int = 0
)



