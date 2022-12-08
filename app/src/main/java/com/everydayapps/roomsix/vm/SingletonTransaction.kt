package com.everydayapps.roomsix.vm

import android.content.Context
import android.content.SharedPreferences
import com.everydayapps.roomsix.db.Member
import com.everydayapps.roomsix.db.Service

class SingletonTransaction(myContext: Context) {



    companion object{
        lateinit var sharedPreferences : SharedPreferences
        lateinit var editor: SharedPreferences.Editor
        private val SHARED_PREF_NAME = "transaction"

        fun initObj(myContext: Context){
            sharedPreferences = myContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE)
            editor = sharedPreferences.edit()
        }
        fun recordMember(selectedMember: Member){

            editor.putString("fname",selectedMember.firstName)
            editor.putString("lname",selectedMember.lastName)
            editor.commit()
        }
        fun recordService(selectedService: Service){
            editor.putString("title",selectedService.serviceTitle)
            editor.putString("price",selectedService.servicePrice)
            editor.commit()
        }
        fun getFullName() :String{
            val fname = sharedPreferences.getString("fname","")
            val lname = sharedPreferences.getString("lname","")
            return "$fname $lname"
        }
        fun getService() :String{
            val service = sharedPreferences.getString("service","")
            return "$service"
        }
        fun getPrice() :String{
            val price = sharedPreferences.getString("price","")
            return "$price"
        }
        fun destroyTransaction(){
            editor.remove(SHARED_PREF_NAME)
        }
    }

}