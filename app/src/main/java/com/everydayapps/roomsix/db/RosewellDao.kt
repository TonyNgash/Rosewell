package com.everydayapps.roomsix.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface RosewellDao {

    @Insert
    fun insertMember(member: Member)

    @Insert
    fun insertService(service: Service)

    @Insert
    fun insertTransaction(transaction: Transaction)


    @Query("SELECT * FROM member_table ORDER BY firstName ASC")
    fun readMembers(): List<Member>

    @Query("SELECT * FROM service_table ORDER BY serviceId ASC")
    fun readServices(): List<Service>

    @Query("SELECT * FROM transactions_table WHERE memberId = :memberId ORDER BY transactionId DESC")
    fun readMemberTransactions(memberId: Int): List<Transaction>

    @Query("SELECT * FROM transactions_table WHERE date = :date ORDER BY transactionId DESC")
    fun readCurrentDayTransactions(date: String): List<Transaction>

    @Query("SELECT * FROM transactions_table WHERE date = :date AND memberId = :memberId ORDER BY transactionId DESC")
    fun readCurrentDayMemberTransactions(date: String, memberId: Int): List<Transaction>

    @Query("SELECT * FROM transactions_table WHERE year_week = :yearWeek AND memberId = :memberId ORDER BY  transactionId DESC")
    fun readWeeklyMemberTransactions(yearWeek: String, memberId: Int): List<Transaction>

    @Query("SELECT * FROM transactions_table ORDER BY date DESC")
    fun readTransactions(): List<Transaction>

    @Query("SELECT * FROM service_table WHERE serviceId = :serviceId")
    fun readServiceName(serviceId: Int):List<Service>

    @Query("SELECT * FROM member_table WHERE memberId = :memberId")
    fun readMemberName(memberId: Int):List<Member>

    @Update
    fun updateMember(member: Member)

    @Update
    fun updateService(service: Service)

    @Delete
    fun deleteMember(member: Member)

    @Delete
    fun deleteService(service: Service)

    @Delete
    fun deleteTransaction(transaction: Transaction)

    @Query("DELETE FROM member_table")
    suspend fun deleteAllMembers()



}