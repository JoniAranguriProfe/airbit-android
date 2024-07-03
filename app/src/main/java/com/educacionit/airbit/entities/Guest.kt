package com.educacionit.airbit.entities

data class Guest(val type: GuestType)

sealed class GuestType
data object Adult : GuestType()
data object Child : GuestType()
data object Baby : GuestType()