package com.everydayapps.roomsix.ui.admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.everydayapps.roomsix.R
import java.text.SimpleDateFormat
import java.util.*
import android.os.CountDownTimer
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.everydayapps.roomsix.db.Transaction
import com.everydayapps.roomsix.vm.RosewellViewModel
import org.w3c.dom.Text
import kotlin.collections.ArrayList


class DashboardFragment : Fragment() {

    lateinit var dashDate : TextView
    lateinit var dashTime : TextView
    lateinit var dashCurrentDayTransactions : TextView
    lateinit var dashPrice : TextView

    lateinit var rosewellViewModel: RosewellViewModel

    var data = ArrayList<Transaction>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDashDate(view)
        getCurrentDashDate(view)
        currentDayTransactions(view)
    }

    fun currentDayTransactions(view: View){
        dashCurrentDayTransactions = view.findViewById(R.id.dash_current_day_transactions)
        dashPrice = view.findViewById(R.id.dash_price)

        rosewellViewModel = ViewModelProvider(this).get(RosewellViewModel::class.java)
        rosewellViewModel.getCurrentDayTransactionsObservers().observe(viewLifecycleOwner, Observer{

            this.data = ArrayList(it)
            val count = data.size
            var price = 0
            println("=========================================================================")

            for(i in 0..count -1){
                println("serviceID: ${data[i].serviceId} -- memberID: ${data[i].memberId} --- Kshs ${data[i].price}")
                price += data[i].price.toInt()
            }
            println("=========================================================================")
            dashCurrentDayTransactions.text = count.toString()
            dashPrice.text = price.toString()
        })
        rosewellViewModel.getAllCurrentDayTransactions()
    }

    fun getCurrentDashDate(view: View){
        object : CountDownTimer(18000000,1000){
            override fun onTick(millisUntilFinished: Long) {
                val sdfTime = SimpleDateFormat("hh:mm:ss a")
                val currentTime = sdfTime.format(Date())
                dashTime = view.findViewById(R.id.dash_time)
                dashTime.text = currentTime
            }

            override fun onFinish() {
                TODO("Not yet implemented")
            }

        }.start()
    }
    fun getDashDate(view: View){
        val now = Date()

        val dow = SimpleDateFormat("EEEE")
        val day = dow.format(now)

        val dom = SimpleDateFormat("dd")
        val date = dom.format(now)

        val moy = SimpleDateFormat("MMMM")
        val month = moy.format(now)


        val year = SimpleDateFormat("yyyy")
        val yea = year.format(now)

        val longDate = "$day, $date $month $yea"

        dashDate = view.findViewById(R.id.dash_date)
        dashDate.text = longDate
    }
    fun frequencyFinder(data: ArrayList<Int>){
        val test = arrayOf(1,2,3,4,2,2,3,1)
        var freq = arrayOf<Int>()
        var visited = -1

    }


}