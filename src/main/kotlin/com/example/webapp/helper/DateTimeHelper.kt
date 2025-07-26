package com.example.webapp.helper

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
object DateTimeHelper {
    fun now(): kotlin.time.Instant = Clock.System.now()

    fun toLocalDateTime(dt: Instant, tz: TimeZone): LocalDateTime = dt.toLocalDateTime(tz)
}
