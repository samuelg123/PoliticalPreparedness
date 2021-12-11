package com.example.android.politicalpreparedness.common.utils

import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtils {
    fun toIso8601String(date: Date, zoneId: ZoneId = ZoneId.systemDefault()): String =
        DateTimeFormatter.ISO_DATE_TIME
            .withLocale(Locale.getDefault())
            .withZone(zoneId)
            .format(date.toInstant())
}