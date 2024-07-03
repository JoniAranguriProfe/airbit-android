package com.educacionit.airbit.reservation.entities

import com.educacionit.airbit.entities.Guest

data class Reservation(
    val roomId: Int,
    val dateInterval: DateInterval,
    val price: Float,
    val guests: List<Guest>
)
