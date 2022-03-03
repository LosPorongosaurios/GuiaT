package com.sergio.guiat.repository

import com.sergio.guiat.GuiaTProject
import com.sergio.guiat.local.Users
import com.sergio.guiat.local.GuiaTDao
import java.sql.Types.NULL

class UsersRepository {

    suspend fun saveUser(name: String, mail: String, cel: String, password: String) {
        val users = Users(
            id = NULL,
            name = name,
            mail = mail,
            cel = cel,
            password = password
        )
        val userDao : GuiaTDao = GuiaTProject.database.GuiaTDao()
        userDao.saveUser(users)

    }

    suspend fun searchUser(email: String): Users {
        val guiatDao: GuiaTDao = GuiaTProject.database.GuiaTDao()
        return guiatDao.searchUser(email)
    }


}