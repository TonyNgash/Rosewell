package com.everydayapps.roomsix.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "member_table")
class Member (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "memberId")
    val memberId: Int = 0,
    @ColumnInfo(name = "firstName")
    val firstName: String,
    @ColumnInfo(name = "lastName")
    val lastName:String
        )