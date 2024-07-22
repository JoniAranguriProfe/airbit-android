package com.educacionit.airbit.reservation.presenter

import com.educacionit.airbit.entities.Guest
import com.educacionit.airbit.entities.Room
import com.educacionit.airbit.reservation.contract.ReservationContract
import com.educacionit.airbit.reservation.entities.DateInterval
import com.educacionit.airbit.reservation.entities.Reservation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit


class ReservationPresenterImpl(
    val reservationModel: ReservationContract.ReservationModel,
    override val view: ReservationContract.ReservationView
) : ReservationContract.ReservationPresenter {

    override fun makeReservation(guests: List<Guest>, dateInterval: DateInterval, room: Room) {
        // TODO: Implement this later
        if (!validateDateInterval(dateInterval)) {
            view.showErrorMessage("La fecha de checkOut debe ser posterior a la de checkIn")
            return
        }
        if (!validateGuests(guests)) {
            view.showErrorMessage("Ingrese al menos un guest")
            return
        }
        val totalPrice = getReservationTotalPrice(room, dateInterval)
        val reservation = Reservation(
            roomId = room.id,
            dateInterval = dateInterval,
            price = totalPrice,
            guests = guests
        )
        val reservationSucceed = reservationModel.makeReservation(reservation)
        if (reservationSucceed) {
            view.showSuccessReservationMessage(reservation)
        } else {
            view.showErrorMessage("No se pudo realizar la reserva, intente nuevamente!")
        }
    }

    private fun getReservationTotalPrice(room: Room, dateInterval: DateInterval): Float {
        val diff: Long = dateInterval.checkOutDate.time - dateInterval.checkInDate.time
        val days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
        return room.pricePerDay * days
    }

    override fun validateDateInterval(dateInterval: DateInterval) =
        dateInterval.checkInDate.before(dateInterval.checkOutDate)

    override fun validateGuests(guests: List<Guest>): Boolean {
        // TODO: Implement this later
        return guests.isNotEmpty()
    }

    override fun configureReservationCountdown() {
        // TODO: Implement this later
    }

    override suspend fun getRoomDetails(roomId: Int) {
        val reservationDetails = withContext(Dispatchers.Main) {
            reservationModel.getRoomDetails(roomId)
        }
        view.onRoomDetailsSuccess(reservationDetails)
    }

}