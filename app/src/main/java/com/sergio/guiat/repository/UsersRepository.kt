package com.sergio.guiat.repository

import com.sergio.guiat.GuiaTProject
import com.sergio.guiat.local.GuiaTDao
import com.sergio.guiat.local.Users

class UsersRepository {

    suspend fun searchUser(email: String): Users {
        val guiaTDao: GuiaTDao = GuiaTProject.database.GuiaTDao()
        val email = guiaTDao.searchUser(email)
        return email

    }
}