package com.educacionit.airbit.reservation.model

import com.educacionit.airbit.entities.Amenity
import com.educacionit.airbit.reservation.contract.ReservationContract
import com.educacionit.airbit.reservation.entities.Reservation
import com.educacionit.airbit.reservation.entities.RoomDetails
import java.util.Date

class ReservationRepository : ReservationContract.ReservationModel {

    override fun makeReservation(reservation: Reservation): Boolean {
        // TODO: Implement this later
        return true
    }

    override fun getRoomDetails(roomId: Int): RoomDetails {
        // Todo: Implement this using a REST API
        return RoomDetails(
            roomId = roomId,
            rate = 4.8f,
            cityName = "Argentina, Buenos Aires",
            capacity = 2,
            availability = listOf(Pair(Date(2024, 7, 12), Date(2024, 7, 31))),
            amenities = listOf(
                Amenity(
                    "Bed",
                    "bed_icon"
                ),
                Amenity(
                    "Wi-Fi",
                    "wifi_icon"
                )
            )
        )
    }
}