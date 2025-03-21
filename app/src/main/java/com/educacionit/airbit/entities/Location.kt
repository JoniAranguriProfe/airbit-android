package com.educacionit.airbit.entities

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

data class Location(val latitude: Float, val longitude: Float) : Serializable {
    fun toLatLng() = LatLng(latitude.toDouble(), longitude.toDouble())
}
