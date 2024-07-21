package com.educacionit.airbit.entities

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

data class Room(val id: Int, val name: String, val location: Location, val pricePerDay: Float) :
    Serializable
