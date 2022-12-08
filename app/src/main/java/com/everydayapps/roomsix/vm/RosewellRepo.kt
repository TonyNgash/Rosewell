package com.everydayapps.roomsix.vm

import androidx.annotation.WorkerThread
import com.everydayapps.roomsix.db.Member
import com.everydayapps.roomsix.db.RosewellDao


class RosewellRepo(private val rosewellDao: RosewellDao) {

    val allMembers: List<Member> = rosewellDao.readMembers()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertMember(member: Member){
        rosewellDao.insertMember(member)
    }
}