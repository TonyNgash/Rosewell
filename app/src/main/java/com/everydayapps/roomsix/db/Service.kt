package com.everydayapps.roomsix.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "service_table")
class Service (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "serviceId")
    val serviceId: Int = 0,
    @ColumnInfo(name = "serviceTitle")
    val serviceTitle : String,
    @ColumnInfo(name = "servicePrice")
    val servicePrice: String
        )