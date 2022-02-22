package com.sergio.guiat.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Users::class], version = 1, exportSchema = false)
abstract class GuiaTDatabase : RoomDatabase() {

    abstract fun GuiaTDao() : GuiaTDao
}