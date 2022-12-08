package com.everydayapps.roomsix.vm

import android.app.Application
import com.everydayapps.roomsix.db.RosewellDatabase
import com.everydayapps.roomsix.vm.RosewellRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RosewellApplication:Application() {

    val applicationScope = CoroutineScope(SupervisorJob())


    val database by lazy { RosewellDatabase.getDatabase(this) }
    val repository by lazy { RosewellRepo(database.rosewellDao()) }
}