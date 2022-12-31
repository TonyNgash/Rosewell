package com.everydayapps.roomsix.vm

import android.app.Application
import androidx.lifecycle.*
import com.everydayapps.roomsix.db.Member
import com.everydayapps.roomsix.db.RosewellDatabase
import com.everydayapps.roomsix.db.Service
import com.everydayapps.roomsix.db.Transaction
import java.text.SimpleDateFormat
import java.util.*

class RosewellViewModel(app: Application):AndroidViewModel(app) {

    var allMembers: MutableLiveData<List<Member>>
    var allServices: MutableLiveData<List<Service>>
    var allTransactions: MutableLiveData<List<Transaction>>
    var allMemberTransactions : MutableLiveData<List<Transaction>>
    var currentDayTransactions : MutableLiveData<List<Transaction>>
    var singleMemberName : MutableLiveData<List<Member>>
    var singleServiceName : MutableLiveData<List<Service>>
    var singleMemberDailyTransaction: MutableLiveData<List<Transaction>>
    var weeklyMemberTransaction: MutableLiveData<List<Transaction>>

    init {
        allMembers = MutableLiveData()
        allServices = MutableLiveData()
        allTransactions = MutableLiveData()
        allMemberTransactions = MutableLiveData()
        currentDayTransactions = MutableLiveData()

        singleMemberName = MutableLiveData()
        singleServiceName = MutableLiveData()

        singleMemberDailyTransaction = MutableLiveData()
        weeklyMemberTransaction = MutableLiveData()

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
    fun getAllMemberTransactionsObervers(): MutableLiveData<List<Transaction>>{
        return allMemberTransactions
    }
    fun getCurrentDayTransactionsObservers(): MutableLiveData<List<Transaction>>{
        return currentDayTransactions
    }
    fun getSingleMemberNameObserver(): MutableLiveData<List<Member>>{
        return singleMemberName
    }
    fun getSingleServiceNameObserver(): MutableLiveData<List<Service>>{
        return singleServiceName
    }
    fun getSingleMemberDailyTransactionObserver(): MutableLiveData<List<Transaction>>{
        return singleMemberDailyTransaction
    }
    fun getWeeklyMemberTransactionObservers(): MutableLiveData<List<Transaction>>{
        return weeklyMemberTransaction
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
    fun getMemberTransactions(memberId: Int){
        val rosewellDao = RosewellDatabase.getDatabase(getApplication()).rosewellDao()
        val list = rosewellDao.readMemberTransactions(memberId)
        allMemberTransactions.postValue(list)
    }
    fun getAllTransactions(){
        val rosewellDao = RosewellDatabase.getDatabase(getApplication()).rosewellDao()
        val list = rosewellDao.readTransactions()
        allTransactions.postValue(list)
    }
    fun getAllCurrentDayTransactions(){
        val sdfDate = SimpleDateFormat("dd/MMMM/yyyy")
        val currentDate = sdfDate.format(Date())
        val rosewellDao = RosewellDatabase.getDatabase(getApplication()).rosewellDao()
        val list = rosewellDao.readCurrentDayTransactions(currentDate)
        currentDayTransactions.postValue(list)
    }
    fun getSingleMemberName(memberId: Int){
        val rosewellDao = RosewellDatabase.getDatabase(getApplication()).rosewellDao()
        val list = rosewellDao.readMemberName(memberId)
        singleMemberName.postValue(list)
    }
    fun getSingleServiceName(serviceId: Int){
        val rosewellDao = RosewellDatabase.getDatabase(getApplication()).rosewellDao()
        val list = rosewellDao.readServiceName(serviceId)
        singleServiceName.postValue(list)
    }

    fun getSingleMemberDailyTransaction(date:String,memberId: Int){
        val rosewellDao = RosewellDatabase.getDatabase(getApplication()).rosewellDao()
        val list = rosewellDao.readCurrentDayMemberTransactions(date,memberId)
        singleMemberDailyTransaction.postValue(list)
    }
    fun getWeeklyMemberTransaction(yearWeek: String, memberId: Int){
        val rosewellDao = RosewellDatabase.getDatabase(getApplication()).rosewellDao()
        val list = rosewellDao.readWeeklyMemberTransactions(yearWeek,memberId)
        weeklyMemberTransaction.postValue(list)
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
    fun deleteTransaction(transaction: Transaction,yearWeek: String,memberId: Int){
        val rosewellDao = RosewellDatabase.getDatabase(getApplication()).rosewellDao()
        rosewellDao.deleteTransaction(transaction)
        getWeeklyMemberTransaction(yearWeek,memberId)
    }

}
