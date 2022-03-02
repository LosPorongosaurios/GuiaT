package com.sergio.guiat.repository

import com.sergio.guiat.GuiaTProject
import com.sergio.guiat.local.Users
import com.sergio.guiat.local.GuiaTDao
import java.sql.Types.NULL

class UsersRepository {

    suspend fun saveUser(name: String, cel: String, email: String, password: String) {
        val users = Users(
            id = NULL,
            name = name,
            email = email,
            cel = cel,
            password = password
        )
        val userDao : GuiaTDao = GuiaTProject.database.GuiaTDao()
        userDao.saveUser(users)

    }

    suspend fun searchUser(emailUser: String): Users {
        val guiaTDao: GuiaTDao = GuiaTProject.database.GuiaTDao()
        return guiaTDao.searchUser(emailUser)
    }


}