package com.sergio.guiat.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.sql.Types

@Entity(tableName = "table_users")
data class Users(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = Types.NULL,
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "email") var author: String = "",
    @ColumnInfo(name = "password") var pages: String = ""
) : Serializable
