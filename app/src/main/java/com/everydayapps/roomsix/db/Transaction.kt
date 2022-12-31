package com.everydayapps.roomsix.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "transactions_table")
class Transaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transactionId")
    val transactionId: Int,
    @ColumnInfo(name = "memberId")
    val memberId : Int,
    @ColumnInfo(name = "serviceId")
    val serviceId: Int,
    @ColumnInfo(name = "fullname")
    val fullname: String,
    @ColumnInfo(name = "service")
    val service : String,
    @ColumnInfo(name = "price")
    val price : String,
    @ColumnInfo(name = "time")
    val time : String,
    @ColumnInfo(name = "date")
    val date : String,
    @ColumnInfo(name = "long_date")
    val longDate : String,
    @ColumnInfo(name = "year_week")
    val yearWeek : String,
    @ColumnInfo(name = "payment_method")
    val payment_method : String
)