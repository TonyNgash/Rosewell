package com.everydayapps.roomsix.ui.admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.everydayapps.roomsix.R
import com.everydayapps.roomsix.db.Service
import com.everydayapps.roomsix.vm.AdminMemberRecyclerAdapter
import com.everydayapps.roomsix.vm.AdminServiceRecyclerAdapter
import com.everydayapps.roomsix.vm.RosewellViewModel


class ServiceFragment : Fragment(), AdminServiceRecyclerAdapter.ServiceRowClickListener {

    lateinit var adminServiceRecyclerView: RecyclerView
    lateinit var adminServiceRecyclerAdapter: AdminServiceRecyclerAdapter
    lateinit var rosewellViewModel: RosewellViewModel

    lateinit var saveServiceBtn: Button
    lateinit var serviceTitle: EditText
    lateinit var servicePrice: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adminServiceRecyclerView = view.findViewById(R.id.adminServiceRecyclerView)

        adminServiceRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adminServiceRecyclerAdapter = AdminServiceRecyclerAdapter(this@ServiceFragment)
            adapter = adminServiceRecyclerAdapter
        }
        rosewellViewModel = ViewModelProvider(this).get(RosewellViewModel::class.java)
        rosewellViewModel.getAllServicessObservers().observe(viewLifecycleOwner, Observer {
            adminServiceRecyclerAdapter.setListData(ArrayList(it))
            adminServiceRecyclerAdapter.notifyDataSetChanged()
        })
        rosewellViewModel.getAllServices()

        serviceTitle = view.findViewById(R.id.serviceTitle)
        servicePrice = view.findViewById(R.id.servicePrice)
        saveServiceBtn = view.findViewById(R.id.saveServiceBtn)
        saveServiceBtn.setOnClickListener{

            val sTitle = serviceTitle.text.toString()
            val sPrice = servicePrice.text.toString()

            if(sTitle.isNotEmpty() && sPrice.isNotEmpty()){
                if(saveServiceBtn.text.equals("Add")){
                    val service = Service(0,sTitle,sPrice)
                    rosewellViewModel.insertService(service)
                }else{
                    val service = Service(serviceTitle.getTag(serviceTitle.id).toString().toInt(),sTitle,sPrice)
                    rosewellViewModel.updateService(service)
                    saveServiceBtn.setText("Add")
                    serviceTitle.setTag(serviceTitle.id,0)
                }
            }else{
                Toast.makeText(context,"You must fill in a service and the price",Toast.LENGTH_LONG).show()
            }
            serviceTitle.setText("")
            servicePrice.setText("")
        }
    }

    override fun onDeleteServiceClickListener(service: Service) {
        rosewellViewModel.deleteService(service)
    }

    override fun onServiceClickListener(service: Service) {
        serviceTitle.setText(service.serviceTitle)
        servicePrice.setText(service.servicePrice)
        serviceTitle.setTag(serviceTitle.id,service.serviceId)
        saveServiceBtn.setText("Update")
    }


}