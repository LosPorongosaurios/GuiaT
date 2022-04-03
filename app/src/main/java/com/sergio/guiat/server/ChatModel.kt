package com.sergio.guiat.server

import java.io.Serializable

data class ChatModel (
    var id: String = "",
    var name: String = "",
    var users: List<String> = emptyList()
): Serializable