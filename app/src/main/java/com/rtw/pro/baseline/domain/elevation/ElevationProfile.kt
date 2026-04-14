package com.rtw.pro.baseline.domain.elevation

data class ElevationProfile(
    val points: List<Double>,
    val totalAscentMeters: Double,
    val totalDescentMeters: Double
)

object ElevationProfileCalculator {
    fun build(rawElevations: List<Double?>): ElevationProfile {
        if (rawElevations.isEmpty()) {
            return ElevationProfile(emptyList(), 0.0, 0.0)
        }

        val filled = interpolateMissing(rawElevations)
        var ascent = 0.0
        var descent = 0.0
        for (i in 1 until filled.size) {
            val diff = filled[i] - filled[i - 1]
            if (diff > 0) ascent += diff else descent += -diff
        }
        return ElevationProfile(
            points = filled,
            totalAscentMeters = ascent,
            totalDescentMeters = descent
        )
    }

    private fun interpolateMissing(raw: List<Double?>): List<Double> {
        val result = raw.map { it ?: Double.NaN }.toMutableList()
        val firstValid = result.indexOfFirst { !it.isNaN() }
        if (firstValid == -1) return List(raw.size) { 0.0 }

        for (i in 0 until firstValid) result[i] = result[firstValid]

        var i = firstValid + 1
        while (i < result.size) {
            if (!result[i].isNaN()) {
                i += 1
                continue
            }
            val start = i - 1
            var end = i
            while (end < result.size && result[end].isNaN()) end += 1

            if (end == result.size) {
                for (k in i until end) result[k] = result[start]
                break
            }

            val startValue = result[start]
            val endValue = result[end]
            val gap = (end - start).toDouble()
            for (k in i until end) {
                val t = (k - start) / gap
                result[k] = startValue + (endValue - startValue) * t
            }
            i = end + 1
        }
        return result
    }
}
