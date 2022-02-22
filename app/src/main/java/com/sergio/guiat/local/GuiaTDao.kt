package com.sergio.guiat.local

import androidx.room.Dao
import androidx.room.Query

@Dao
interface GuiaTDao {

    @Query("SELECT * FROM table_users WHERE name LIKE :email")
    suspend fun searchUser(email: String): Users
}