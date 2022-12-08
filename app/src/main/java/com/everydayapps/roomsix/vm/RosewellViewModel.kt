package com.everydayapps.roomsix.vm

import android.app.Application
import androidx.lifecycle.*
import com.everydayapps.roomsix.db.Member
import com.everydayapps.roomsix.db.RosewellDatabase
import com.everydayapps.roomsix.db.Service
import com.everydayapps.roomsix.db.Transaction

class RosewellViewModel(app: Application):AndroidViewModel(app) {

    lateinit var allMembers: MutableLiveData<List<Member>>
    lateinit var allServices: MutableLiveData<List<Service>>
    lateinit var allTransactions: MutableLiveData<List<Transaction>>

    init {
        allMembers = MutableLiveData()
        allServices = MutableLiveData()
        allTransactions = MutableLiveData()

    }

    fun getAllMembersObservers(): MutableLiveData<List<Member>>{
        return allMembers
    }

    fun getAllServicessObservers(): MutableLiveData<List<Service>>{
        return allServices
    }

    fun getAllTransactionsObservers(): MutableLiveData<List<Transaction>>{
        return allTransactions
    }

    fun getAllMembers(){
        val rosewellDao = RosewellDatabase.getDatabase((getApplication())).rosewellDao()
        val list = rosewellDao.readMembers()

        allMembers.postValue(list)
    }
    fun getAllServices(){
        val rosewellDao = RosewellDatabase.getDatabase((getApplication())).rosewellDao()
        val list = rosewellDao.readServices()
        allServices.postValue(list)
    }
    fun getAllTransactions(){
        val rosewellDao = RosewellDatabase.getDatabase(getApplication()).rosewellDao()
        val list = rosewellDao.readTransactions()
        allTransactions.postValue(list)
    }
    fun insertMember(member: Member){
        val rosewellDao = RosewellDatabase.getDatabase(getApplication()).rosewellDao()
        rosewellDao.insertMember(member)
        getAllMembers()
    }
    fun insertService(service: Service){
        val rosewellDao = RosewellDatabase.getDatabase((getApplication())).rosewellDao()
        rosewellDao.insertService(service)
        getAllServices()
    }
    fun inserTransaction(transaction: Transaction){
        val rosewellDao = RosewellDatabase.getDatabase(getApplication()).rosewellDao()
        rosewellDao.insertTransaction(transaction)
    }

    fun updateMember(member: Member){
        val rosewellDao = RosewellDatabase.getDatabase(getApplication()).rosewellDao()
        rosewellDao.updateMember(member)
        getAllMembers()
    }
    fun updateService(service: Service){
        val rosewellDao = RosewellDatabase.getDatabase(getApplication()).rosewellDao()
        rosewellDao.updateService(service)
        getAllServices()
    }

    fun deleteMember(member: Member){
        val rosewellDao = RosewellDatabase.getDatabase(getApplication()).rosewellDao()
        rosewellDao.deleteMember(member)
        getAllMembers()
    }
    fun deleteService(service: Service){
        val rosewellDao = RosewellDatabase.getDatabase(getApplication()).rosewellDao()
        rosewellDao.deleteService(service)
        getAllServices()
    }

}
