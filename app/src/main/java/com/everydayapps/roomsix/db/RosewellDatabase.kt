package com.everydayapps.roomsix.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Member::class, Service::class, Transaction::class], version = 1)
abstract class RosewellDatabase : RoomDatabase(){

    abstract fun rosewellDao(): RosewellDao

    companion object{

        @Volatile
        private var INSTANCE : RosewellDatabase? = null

        fun getDatabase(context: Context) : RosewellDatabase {
            return INSTANCE ?:  Room.databaseBuilder<RosewellDatabase>(
                    context.applicationContext,
                    RosewellDatabase::class.java,
                    "rosewell_db"
            ).allowMainThreadQueries().build()
        }
    }
}
