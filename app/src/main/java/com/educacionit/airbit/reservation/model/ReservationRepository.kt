package com.educacionit.airbit.reservation.model

import com.educacionit.airbit.reservation.contract.ReservationContract
import com.educacionit.airbit.reservation.entities.Reservation

class ReservationRepository : ReservationContract.ReservationModel {

    override fun makeReservation(reservation: Reservation): Boolean {
        // TODO: Implement this later
        return true
    }
}