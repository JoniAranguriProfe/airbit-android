package com.educacionit.airbit.home.presenter

import com.educacionit.airbit.common.CoroutineTestRule
import com.educacionit.airbit.entities.Location
import com.educacionit.airbit.entities.Room
import com.educacionit.airbit.home.contract.HomeContract
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class HomePresenterImplTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var homeModelMock: HomeContract.HomeModel
    private lateinit var homeViewMock: HomeContract.HomeView
    private lateinit var homePresenterImpl: HomePresenterImpl

    @Before
    fun setUp() {
        homeModelMock = Mockito.mock(HomeContract.HomeModel::class.java)
        homeViewMock = Mockito.mock(HomeContract.HomeView::class.java)
        homePresenterImpl = HomePresenterImpl(
            homeModel = homeModelMock,
            view = homeViewMock,
            dispatcher = coroutineTestRule.testDispatcher
        )
    }

    @Test
    fun `getCurrentLocation returns same LatLng as the model`() = runBlocking {
        // Given
        val expectedLatitude = 1.0
        val expectedLongitude = 3.0
        val expectedLatLng = LatLng(expectedLatitude, expectedLongitude)
        Mockito.`when`(homeModelMock.getCurrentLocation())
            .thenReturn(Location(expectedLatitude.toFloat(), expectedLongitude.toFloat()))

        // When
        val generatedLatLng = homePresenterImpl.getCurrentLocation()

        //Then
        assertEquals(expectedLatLng, generatedLatLng)
    }

    @Test
    fun `getRoomsForPlace calls the showRoomsInMap method of the view`() = runBlocking {
        // Given
        val mockedLocation = Mockito.mock(Location::class.java)
        val mockedRoomList = listOf<Room>()
        Mockito.`when`(homeModelMock.getRoomsForPlace(mockedLocation)).thenReturn(mockedRoomList)

        // When
        homePresenterImpl.getRoomsForPlace(mockedLocation)

        // Then
        Mockito.verify(homeViewMock).showRoomsInMap(mockedRoomList)
    }
}