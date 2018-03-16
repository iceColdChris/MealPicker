package com.cfahlin.mealpicker.api.model

import java.util.*

data class Results(val results: Array<Place>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Results

        if (!Arrays.equals(results, other.results)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(results)
    }
}