package com.educacionit.airbit.reservation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.educacionit.airbit.R
import com.educacionit.airbit.reservation.contract.ReservationContract
import com.educacionit.airbit.reservation.entities.Reservation
import com.educacionit.airbit.reservation.model.ReservationRepository
import com.educacionit.airbit.reservation.presenter.ReservationPresenterImpl

class ReservationActivity : AppCompatActivity(), ReservationContract.ReservationView {
    private lateinit var reservationPresenter: ReservationContract.ReservationPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)
        initPresenter()
    }

    override fun initPresenter() {
        val reservationModel: ReservationContract.ReservationModel = ReservationRepository()
        reservationPresenter =
            ReservationPresenterImpl(reservationModel = reservationModel, view = this)
    }

    override fun showSuccessReservationMessage(reservation: Reservation) {
        // TODO: Implement this later
    }

    override fun showReservationReminder() {
        // TODO: Implement this later
    }

    override fun getViewContext() = this

    override fun showNoInternetMessage() {
        // Todo: Implement logic to close the app
        showErrorMessage("Sin internet, desea salir?")
    }
}