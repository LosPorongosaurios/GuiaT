package com.sergio.guiat.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.sql.Types.NULL

@Entity(tableName = "table_users")
data class Users(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = NULL,
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "mail") var mail: String = "",
    @ColumnInfo(name = "cel") var cel: String = "",
    @ColumnInfo(name = "password") var password: String = ""

) : Serializable
