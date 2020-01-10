package com.airwallex.rxdemo

import org.springframework.data.annotation.Id
import java.time.ZonedDateTime
import java.util.UUID

data class Customer(
    @Id
    val id: UUID? = null,
    val name: String,
    val email: String? = null,
    val address: String? = null,
    val createTime: ZonedDateTime = ZonedDateTime.now(),
    val lastUpdate: ZonedDateTime = ZonedDateTime.now()
)
