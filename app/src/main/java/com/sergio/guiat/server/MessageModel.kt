package com.sergio.guiat.server

import java.util.*

data class MessageModel(
    var message: String = "",
    var from: String = "",
    var dob: Date = Date()
)
