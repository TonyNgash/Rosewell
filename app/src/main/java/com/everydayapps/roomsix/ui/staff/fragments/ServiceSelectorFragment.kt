package com.everydayapps.roomsix.ui.staff.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.everydayapps.roomsix.R
import com.everydayapps.roomsix.db.Service
import com.everydayapps.roomsix.db.Transaction
import com.everydayapps.roomsix.vm.RosewellViewModel
import com.everydayapps.roomsix.vm.ServiceSelectorRecyclerViewAdapter
import com.everydayapps.roomsix.vm.SingletonTransaction
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class ServiceSelectorFragment : Fragment() , ServiceSelectorRecyclerViewAdapter.ServiceSelectorClickListener{

    lateinit var serviceSelectorRecyclerView: RecyclerView
    lateinit var serviceSelectorRecyclerViewAdapter: ServiceSelectorRecyclerViewAdapter
    lateinit var rosewellViewModel: RosewellViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service_selector, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        serviceSelectorRecyclerView = view.findViewById(R.id.serviceSelectorRecyclerView)

        serviceSelectorRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            serviceSelectorRecyclerViewAdapter = ServiceSelectorRecyclerViewAdapter(this@ServiceSelectorFragment,view)
            adapter = serviceSelectorRecyclerViewAdapter
        }

        rosewellViewModel = ViewModelProvider(this).get(RosewellViewModel::class.java)
        rosewellViewModel.getAllServicessObservers().observe(viewLifecycleOwner, Observer {
            serviceSelectorRecyclerViewAdapter.setListData(ArrayList(it))
            serviceSelectorRecyclerViewAdapter.notifyDataSetChanged()
        })
        rosewellViewModel.getAllServices()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onServiceSelectorListener(service: Service,myView :View) {
        val name = "${service.serviceTitle} ${service.servicePrice}"

        SingletonTransaction.recordService(service)

        val fullName = SingletonTransaction.getFullName()
        val service = SingletonTransaction.getService()
        val price = SingletonTransaction.getPrice()

        val sdfDate = SimpleDateFormat("dd/mm/yyyy")
        val currentDate = sdfDate.format(Date())
        val sdfTime = SimpleDateFormat("HH:mm:ss")
        val currentTime = sdfTime.format(Date())
//        val current = LocalDateTime.now()
//        val dateFormater = DateTimeFormatter.ofPattern("dd/mm/yyyy")
//        val timeFormater = DateTimeFormatter.ofPattern("HH:mm:ss")
//
//        val dateFormated = current.format(dateFormater)
//        val timeFormated = current.format(timeFormater)

        //gather app the info (fullname, service, price, timestamp)

        val transaction = Transaction(0,fullName,service,price, currentTime,currentDate )
        rosewellViewModel.inserTransaction(transaction)

        Toast.makeText(context,"Successfully added a transaction", Toast.LENGTH_LONG).show()

        Navigation.findNavController(myView).navigate(R.id.action_serviceSelectorFragment_to_memberSelectorFragment)
    }
}