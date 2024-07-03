package com.educacionit.airbit.entities

import com.google.android.gms.maps.model.LatLng

data class Room(val id: Int, val name:String, val location: LatLng, val pricePerDay: Float)
