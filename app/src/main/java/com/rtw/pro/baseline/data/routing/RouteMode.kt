package com.rtw.pro.baseline.data.routing

enum class RouteMode {
    DRIVING,
    CYCLING,
    FOOT;

    fun toOsrmProfilePath(): String {
        return when (this) {
            DRIVING -> "routed-car"
            CYCLING -> "routed-bike"
            FOOT -> "routed-foot"
        }
    }

    companion object {
        fun fromUiMode(mode: String): RouteMode {
            return when (mode.lowercase()) {
                "cycling", "bike" -> CYCLING
                "foot", "walk" -> FOOT
                else -> DRIVING
            }
        }
    }
}
