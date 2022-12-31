package com.everydayapps.roomsix.ui.staff.fragments

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
import com.everydayapps.roomsix.vm.StaffDailyTransactionAdapter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.sign


class ServiceSelectorFragment : Fragment() , ServiceSelectorRecyclerViewAdapter.ServiceSelectorClickListener{

    lateinit var serviceSelectorRecyclerView: RecyclerView
    lateinit var staffDailyTransactionRecyclerView: RecyclerView

    lateinit var serviceSelectorRecyclerViewAdapter: ServiceSelectorRecyclerViewAdapter
    lateinit var staffDailyTransactionAdapter: StaffDailyTransactionAdapter

    lateinit var rosewellViewModel: RosewellViewModel

    lateinit var paymentMethodDialog: Dialog
    lateinit var confrimationDialog: Dialog

    val memberId = SingletonTransaction.getMemberId()
    val fullName = SingletonTransaction.getFullName()

    val dow = SimpleDateFormat("EEEE")
    val now = Date()
    val day = dow.format(now)

    val moy = SimpleDateFormat("MMMM")
    val month = moy.format(now)

    val dom = SimpleDateFormat("dd")
    val date = dom.format(now)

    val year = SimpleDateFormat("yyyy")
    val yea = year.format(now)

    val sdfDate = SimpleDateFormat("dd/MMMM/yyyy")
    val currentDate = sdfDate.format(Date())
    val sdfTime = SimpleDateFormat("HH:mm:ss")
    val currentTime = sdfTime.format(Date())

    val week = Calendar.getInstance(TimeZone.getTimeZone("EAT")).get(Calendar.WEEK_OF_YEAR)

    var transactionData = ArrayList<Transaction>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service_selector, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dailyViewTitle = view.findViewById<TextView>(R.id.dailyViewTitle)
        val dailyStaffTotal = view.findViewById<TextView>(R.id.dailyStaffTotal)

        dailyViewTitle.text = "Today's Services By: $fullName"


        serviceSelectorRecyclerView = view.findViewById(R.id.serviceSelectorRecyclerView)
        staffDailyTransactionRecyclerView = view.findViewById(R.id.staffDailyTransactionsRecyclerView)


        serviceSelectorRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            serviceSelectorRecyclerViewAdapter = ServiceSelectorRecyclerViewAdapter(this@ServiceSelectorFragment,view)
            adapter = serviceSelectorRecyclerViewAdapter
        }

        staffDailyTransactionRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            staffDailyTransactionAdapter = StaffDailyTransactionAdapter()
            adapter = staffDailyTransactionAdapter
        }

        rosewellViewModel = ViewModelProvider(this).get(RosewellViewModel::class.java)

        rosewellViewModel.getAllServicessObservers().observe(viewLifecycleOwner, Observer {
            serviceSelectorRecyclerViewAdapter.setListData(ArrayList(it))
            serviceSelectorRecyclerViewAdapter.notifyDataSetChanged()
        })
        rosewellViewModel.getAllServices()

        rosewellViewModel.getSingleMemberDailyTransactionObserver().observe(viewLifecycleOwner, Observer {
            transactionData = ArrayList(it)
            var dailyTotal = 0
            for(i in 0..transactionData.size -1){
                dailyTotal += transactionData[i].price.toInt()
            }
            dailyStaffTotal.text = "Total: Kshs. ${dailyTotal.toString()}"

            staffDailyTransactionAdapter.setListData(ArrayList(it))
            staffDailyTransactionAdapter.notifyDataSetChanged()
        })
        rosewellViewModel.getSingleMemberDailyTransaction(currentDate,memberId)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onServiceSelectorListener(service: Service,myView :View) {

        paymentMethodDialog(service,myView)
    }
    fun paymentMethodDialog(service: Service,myView: View){
        paymentMethodDialog = Dialog(requireContext())
        paymentMethodDialog.setContentView(R.layout.payment_method_dialog)
        paymentMethodDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val mpesa = paymentMethodDialog.findViewById<ImageView>(R.id.mpesa)
        val cash = paymentMethodDialog.findViewById<ImageView>(R.id.cash)
        val cancel = paymentMethodDialog.findViewById<Button>(R.id.cancel_payment)

        mpesa.setOnClickListener {
//            SingletonTransaction.initObj(requireContext())
            SingletonTransaction.recordPaymentMethod("M-Pesa")
            paymentMethodDialog.dismiss()
            confrimationDialog(service,myView)
        }
        cash.setOnClickListener {
//            SingletonTransaction.initObj(requireContext())
            SingletonTransaction.recordPaymentMethod("Cash")
            paymentMethodDialog.dismiss()
            confrimationDialog(service,myView)
        }
        cancel.setOnClickListener{
            paymentMethodDialog.dismiss()
        }
        paymentMethodDialog.show()
    }
    fun confrimationDialog(service: Service,myView: View){
        confrimationDialog = Dialog(requireContext())
        confrimationDialog.setContentView(R.layout.confirmation_dialog)
        confrimationDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val selectingMemberName = confrimationDialog.findViewById<TextView>(R.id.selectingMemberName)
        val selectedService = confrimationDialog.findViewById<TextView>(R.id.selectedService)
        val selectedServicePrice = confrimationDialog.findViewById<TextView>(R.id.selectedServicePrice)
        val selectedPaymentMethod = confrimationDialog.findViewById<TextView>(R.id.selectedPaymentMethod)

        val paymentMethod = SingletonTransaction.getPaymentMethod()
        selectingMemberName.text = fullName
        selectedService.text = service.serviceTitle
        selectedServicePrice.text = service.servicePrice
        selectedPaymentMethod.text = paymentMethod

        val cancelBtn = confrimationDialog.findViewById<Button>(R.id.cancel_confirmation)
        val confrimBtn = confrimationDialog.findViewById<Button>(R.id.confirm_transaction)

        cancelBtn.setOnClickListener {
            confrimationDialog.dismiss()
        }
        confrimBtn.setOnClickListener {

            val longDate = "$day, $date $month $yea"
            val yearWeek = yea +"_" + week

            //Toast.makeText(context,"Its week: $yearWeek",Toast.LENGTH_LONG).show()

            val transaction = Transaction(0,memberId,service.serviceId,fullName,service.serviceTitle,service.servicePrice, currentTime,currentDate, longDate,yearWeek,paymentMethod )
            rosewellViewModel.inserTransaction(transaction)
            confrimationDialog.dismiss()

            Toast.makeText(context,"Successfully added a transaction", Toast.LENGTH_LONG).show()

            Navigation.findNavController(myView).navigate(R.id.action_serviceSelectorFragment_to_memberSelectorFragment)
        }

        confrimationDialog.show()
    }
}