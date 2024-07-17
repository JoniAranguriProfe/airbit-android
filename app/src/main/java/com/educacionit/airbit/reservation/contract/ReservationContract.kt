package com.educacionit.airbit.reservation.contract

import com.educacionit.airbit.base.contract.BaseContract
import com.educacionit.airbit.entities.Guest
import com.educacionit.airbit.entities.Room
import com.educacionit.airbit.reservation.entities.DateInterval
import com.educacionit.airbit.reservation.entities.Reservation
import com.educacionit.airbit.reservation.entities.RoomDetails
import java.util.Date

interface ReservationContract {
    interface ReservationView : BaseContract.BaseView {
        // For Presenter usage
        fun showSuccessReservationMessage(reservation: Reservation)
        fun onRoomDetailsSuccess(roomDetails: RoomDetails)

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
        suspend fun getRoomDetails(roomId: Int)
    }

    interface ReservationModel : BaseContract.BaseModel {
        fun makeReservation(reservation: Reservation): Boolean
        fun getRoomDetails(roomId: Int): RoomDetails
    }
}