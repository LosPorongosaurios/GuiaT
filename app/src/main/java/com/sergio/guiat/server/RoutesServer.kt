package com.sergio.guiat.server

import java.io.Serializable

data class RoutesServer(
    var id: String? = null,
    var name: String? = null,
    var guide: String? = null,
    var description: String? = null,
    var sites: String? = null,
    var schedule: String? = null,
    var urlPicture: String? = null

) : Serializable
