package com.educacionit.airbit.reservation.entities

import com.educacionit.airbit.entities.Amenity
import java.util.Date

data class RoomDetails(
    val roomId: Int,
    val rate: Float,
    val cityName: String,
    val capacity: Int,
    val availability: List<Pair<Date, Date>>,
    val amenities: List<Amenity>
)