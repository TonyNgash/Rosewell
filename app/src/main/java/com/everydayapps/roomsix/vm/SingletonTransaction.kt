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
            editor.putInt("memberid",selectedMember.memberId)
            editor.commit()
        }
        fun recordMemberTransactionDetails(member: Member){
            editor.putInt("tmid",member.memberId)
            editor.putString("tfname",member.firstName)
            editor.putString("tlname",member.lastName)
            editor.commit()
        }
        fun recordPaymentMethod(method: String){
            editor.putString("paymentMethod",method)
            editor.commit()
        }
        fun getPaymentMethod():String{
            val method = sharedPreferences.getString("paymentMethod","")
            return method.toString()
        }
        fun setPassword(password : String){
            editor.putString("admin_password",password)
            editor.commit()
        }
        fun getPassword():String{
            val pass = sharedPreferences.getString("admin_password","98765432")
            return pass.toString()
        }
        fun getTmid():Int{
            return sharedPreferences.getInt("tmid",0)
        }
        fun getMemberTransactionFullname(): String{
            val tfname = sharedPreferences.getString("tfname","")
            val tlname = sharedPreferences.getString("tlname","")
            return "$tfname $tlname"
        }
        fun getFullName() :String{
            val fname = sharedPreferences.getString("fname","")
            val lname = sharedPreferences.getString("lname","")
            return "$fname $lname"
        }
        fun getMemberId(): Int{
            return sharedPreferences.getInt("memberid",0)
        }
        fun getService() :String{
            val service = sharedPreferences.getString("title","")
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