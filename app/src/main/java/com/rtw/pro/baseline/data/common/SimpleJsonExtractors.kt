package com.rtw.pro.baseline.data.common

object SimpleJsonExtractors {
    fun extractFirstString(json: String, key: String): String? {
        val pattern = Regex(""""$key"\s*:\s*"([^"]*)"""")
        return pattern.find(json)?.groupValues?.getOrNull(1)
    }

    fun extractFirstNumber(json: String, key: String): Double? {
        val pattern = Regex(""""$key"\s*:\s*(-?\d+(?:\.\d+)?)""")
        return pattern.find(json)?.groupValues?.getOrNull(1)?.toDoubleOrNull()
    }

    fun extractNumberArrayByObjectKey(json: String, objectKey: String, key: String): List<Double> {
        val objectPattern = Regex("""\{[^{}]*"$key"\s*:\s*(-?\d+(?:\.\d+)?)\s*[^{}]*\}""")
        if (!json.contains(""""$objectKey"""")) return emptyList()
        return objectPattern.findAll(json).mapNotNull { it.groupValues.getOrNull(1)?.toDoubleOrNull() }.toList()
    }
}
