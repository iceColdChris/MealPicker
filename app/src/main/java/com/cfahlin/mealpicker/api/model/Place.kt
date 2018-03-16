package com.cfahlin.mealpicker.api.model

data class Place(val name: String, val opening_hours: Open)
data class Open(val open_now: Boolean)