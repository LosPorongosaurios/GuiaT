package com.sergio.guiat

import android.app.Application
import androidx.room.Room
import com.sergio.guiat.local.GuiaTDatabase

class GuiaTProject : Application() {

    companion object {
        lateinit var database: GuiaTDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            GuiaTDatabase::class.java,
            "guiat_db"
        ).build()
    }
}