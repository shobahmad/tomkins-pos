package com.erebor.tomkins.pos.data.converters

import java.util.UUID

object Generators {
    val randomUUID: String
        get() {
            val uuid = UUID.randomUUID()
            val uuidString = uuid.toString()

            return uuidString.replace("-".toRegex(), "")
        }
}
