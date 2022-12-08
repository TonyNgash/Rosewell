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

    @Query("SELECT * FROM transactions_table ORDER BY transactionId DESC")
    fun readTransactions(): List<Transaction>

    @Update
    fun updateMember(member: Member)

    @Update
    fun updateService(service: Service)

    @Delete
    fun deleteMember(member: Member)

    @Delete
    fun deleteService(service: Service)

    @Query("DELETE FROM member_table")
    suspend fun deleteAllMembers()

}