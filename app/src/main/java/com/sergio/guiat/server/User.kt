package com.sergio.guiat.server

import java.io.Serializable

data class User(
    var uid : String? = null,
    var name: String? = null,
    var email: String? = null,
    var cel: String? = null,
) : Serializable
