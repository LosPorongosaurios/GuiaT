package com.sergio.guiat.local

import androidx.room.*

@Dao
interface GuiaTDao {

    @Insert
    suspend fun saveUser(users: Users)

    @Query("SELECT * FROM table_users WHERE mail LIKE :email")
    suspend fun searchUser(email: String): Users

}
