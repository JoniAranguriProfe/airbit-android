package com.educacionit.airbit.reservation.contract

import com.educacionit.airbit.base.contract.BaseContract
import com.educacionit.airbit.entities.Guest
import com.educacionit.airbit.entities.Room
import com.educacionit.airbit.reservation.entities.DateInterval
import com.educacionit.airbit.reservation.entities.Reservation
import java.util.Date

interface ReservationContract {
    interface ReservationView : BaseContract.BaseView {
        // For Presenter usage
        fun showSuccessReservationMessage(reservation: Reservation)

        // For internal usage
        fun showReservationReminder()
    }

    interface ReservationPresenter : BaseContract.BasePresenter<ReservationView> {
        // For View usage
        fun makeReservation(guests: List<Guest>, dateInterval: DateInterval, room: Room)
        // For internal usage
        fun validateDateInterval(dateInterval: DateInterval): Boolean
        fun validateGuests(guests: List<Guest>): Boolean
        fun configureReservationCountdown()
    }

    interface ReservationModel : BaseContract.BaseModel {
        fun makeReservation(reservation: Reservation): Boolean
    }
}