package com.educacionit.airbit.reservation.view

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.SystemClock
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.educacionit.airbit.R
import com.educacionit.airbit.base.common.NotificationFactory
import com.educacionit.airbit.entities.Room
import com.educacionit.airbit.home.view.HomeActivity.Companion.SELECTED_ROOM_EXTRA
import com.educacionit.airbit.reservation.contract.ReservationContract
import com.educacionit.airbit.reservation.entities.Reservation
import com.educacionit.airbit.reservation.entities.RoomDetails
import com.educacionit.airbit.reservation.model.InternetReceiver
import com.educacionit.airbit.reservation.model.ReservationRepository
import com.educacionit.airbit.reservation.presenter.ReservationPresenterImpl
import com.educacionit.airbit.reservation.view.adapters.AmenitiesAdapter
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class ReservationActivity : AppCompatActivity(), ReservationContract.ReservationView {
    private lateinit var roomAmenitiesAdapter: AmenitiesAdapter
    private lateinit var reservationPresenter: ReservationContract.ReservationPresenter
    private lateinit var roomTitle: TextView
    private lateinit var roomPriceNight: TextView
    private lateinit var roomLocationName: TextView
    private lateinit var ratingLabel: TextView
    private lateinit var roomRate: AppCompatRatingBar
    private lateinit var roomAmenitiesRecyclerView: RecyclerView
    private var receiver: InternetReceiver? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)
        roomTitle = findViewById(R.id.room_title)
        roomPriceNight = findViewById(R.id.room_night_price)
        roomLocationName = findViewById(R.id.room_location_name)
        ratingLabel = findViewById(R.id.rating_label)
        roomRate = findViewById(R.id.room_rate)
        roomAmenitiesRecyclerView = findViewById(R.id.room_amenities)

        initPresenter()

        configureViews()
        configureReservationAlarm()
    }

    private fun configureReservationAlarm() {
        val selfIntent = Intent(this, ReservationActivity::class.java)
        // Todo: Validate why the extras are being deleted
        selfIntent.putExtra(TITLE_EXTRA, "La sesión está por finalizar")
        selfIntent.putExtra(CONTENT_EXTRA, "Realiza la reserva y recibe un descuento!")
        selfIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmTargetTime = SystemClock.elapsedRealtime() + 3000
        alarmManager.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            alarmTargetTime,
            pendingIntent
        )
    }

    override fun onNewIntent(newIntent: Intent?) {
        super.onNewIntent(newIntent)
        newIntent?.extras?.apply {
            val notificationTitle =
                "La sesión está por finalizar"//getString(TITLE_EXTRA) ?: "No message"
            val notificationContent =
                "Realiza la reserva y recibe un descuento!" // getString(CONTENT_EXTRA) ?: ""
            NotificationFactory.showNotification(
                this@ReservationActivity,
                notificationTitle,
                notificationContent
            )

        }
    }

    private fun configureViews() {
        roomAmenitiesAdapter = AmenitiesAdapter()
        roomAmenitiesRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        roomAmenitiesRecyclerView.adapter = roomAmenitiesAdapter

        (intent.extras?.getSerializable(SELECTED_ROOM_EXTRA) as? Room)?.apply {
            roomTitle.text = name
            roomPriceNight.text = pricePerDay.toString()

            lifecycleScope.launch {
                reservationPresenter.getRoomDetails(id)
            }
        }
    }

    override fun initPresenter() {
        val reservationModel: ReservationContract.ReservationModel = ReservationRepository()
        reservationPresenter =
            ReservationPresenterImpl(reservationModel = reservationModel, view = this)

        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        receiver = InternetReceiver()
        this.registerReceiver(receiver, filter)
    }

    override fun showSuccessReservationMessage(reservation: Reservation) {
        // TODO: Implement this later
    }

    override fun onRoomDetailsSuccess(roomDetails: RoomDetails) {
        roomDetails.apply {
            roomLocationName.text = cityName
            roomRate.rating = rate
            roomRate.numStars = rate.roundToInt()
            ratingLabel.text = rate.toString()
            roomAmenitiesAdapter.setAmenities(amenities)
        }
    }

    override fun showReservationReminder() {
        // TODO: Implement this later
    }

    override fun getViewContext() = this

    override fun showNoInternetMessage() {
        // Todo: Implement logic to close the app
        showErrorMessage("Sin internet, desea salir?")
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    companion object {
        const val TITLE_EXTRA = "TITLE_EXTRA"
        const val CONTENT_EXTRA = "CONTENT_EXTRA"
    }
}