package com.sergio.guiat.local

import androidx.room.*

@Dao
interface GuiaTDao {

    @Insert
    suspend fun saveUser(users: Users)

    @Query("SELECT * FROM TABLE_USERS WHERE email LIKE :email")
    suspend fun searchUser(email: String): Users
}