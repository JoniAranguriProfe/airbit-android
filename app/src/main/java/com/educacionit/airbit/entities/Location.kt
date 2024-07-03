package com.educacionit.airbit.entities

import com.google.android.gms.maps.model.LatLng

data class Location(val latitude: Float, val longitude: Float) {
    fun toLatLng() = LatLng(latitude.toDouble(), longitude.toDouble())
}
