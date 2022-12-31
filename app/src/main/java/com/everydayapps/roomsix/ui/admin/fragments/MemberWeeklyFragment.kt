package com.everydayapps.roomsix.ui.admin.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.everydayapps.roomsix.R
import com.everydayapps.roomsix.db.Transaction
import com.everydayapps.roomsix.vm.RosewellViewModel
import com.everydayapps.roomsix.vm.SingletonTransaction
import com.everydayapps.roomsix.vm.WeeklyRecyclerAdapter
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.time.temporal.WeekFields
import java.util.*
import kotlin.collections.ArrayList

class MemberWeeklyFragment : Fragment(),WeeklyRecyclerAdapter.WeeklyRowClickListener {

    lateinit var weeklyRecyclerView: RecyclerView
    lateinit var weeklyRecyclerViewAdapter: WeeklyRecyclerAdapter
    lateinit var rosewellViewModel: RosewellViewModel
    lateinit var weeklyViewMemberName: TextView
    lateinit var show_all_transactions_btn: Button
    lateinit var weeklyTotals: TextView

    val memberId = SingletonTransaction.getTmid()
    val week = Calendar.getInstance(TimeZone.getTimeZone("EAT")).get(Calendar.WEEK_OF_YEAR)
    val year = SimpleDateFormat("yyyy")
    val now = Date()
    val yea = year.format(now)
    val yeahWeek = yea+"_"+week
    var transactionData = ArrayList<Transaction>()

    lateinit var confirmTransactionDeleteDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_member_weekly, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weeklyTotals = view.findViewById(R.id.weeklyTotals)
        weeklyViewMemberName = view.findViewById(R.id.weeklyViewMemberName)

        show_all_transactions_btn =  view.findViewById(R.id.show_all_transactions_btn)
        show_all_transactions_btn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_weeklyTransactionsFragment_to_transactionFragment)
        }

        weeklyRecyclerView = view.findViewById(R.id.weeklyRecyclerView)
        weeklyRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            weeklyRecyclerViewAdapter = WeeklyRecyclerAdapter(this@MemberWeeklyFragment)
            adapter = weeklyRecyclerViewAdapter
        }
        rosewellViewModel = ViewModelProvider(this).get(RosewellViewModel::class.java)
        rosewellViewModel.getWeeklyMemberTransactionObservers().observe(viewLifecycleOwner, Observer {
            transactionData = ArrayList(it)
            var weeklyTotal = 0
            for(i in 0..transactionData.size -1){
                weeklyTotal += transactionData[i].price.toInt()
            }
            weeklyTotals.text = "Total: Kshs. ${ weeklyTotal.toString() }"
            weeklyRecyclerViewAdapter.setListData(ArrayList(it))
            weeklyRecyclerViewAdapter.notifyDataSetChanged()
        })

        weeklyViewMemberName.text = "Week $week of the Year $yea By: "+ SingletonTransaction.getMemberTransactionFullname()
        rosewellViewModel.getWeeklyMemberTransaction(yeahWeek,memberId)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Toast.makeText(requireContext(),"Back Pressed",Toast.LENGTH_LONG).show()
            }

        })

    }

    override fun onDeleteWeeklyTransaction(transaction: Transaction) {
        confirmTransactionDeleteDialog = Dialog(requireContext())
        confirmTransactionDeleteDialog.setContentView(R.layout.delete_dialog)
        confirmTransactionDeleteDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val deleteService = confirmTransactionDeleteDialog.findViewById<TextView>(R.id.deleteService)
        val deletePrice = confirmTransactionDeleteDialog.findViewById<TextView>(R.id.deletePrice)
        val deletePaymentMethod = confirmTransactionDeleteDialog.findViewById<TextView>(R.id.deletePaymentMethod)

        deleteService.text = transaction.service
        deletePrice.text = "Khs. ${transaction.price}"
        deletePaymentMethod.text = transaction.payment_method

        val cancel_delete = confirmTransactionDeleteDialog.findViewById<Button>(R.id.cancel_delete)
        val confirm_delete = confirmTransactionDeleteDialog.findViewById<Button>(R.id.confirm_delete)

        cancel_delete.setOnClickListener {
            confirmTransactionDeleteDialog.dismiss()
        }
        confirm_delete.setOnClickListener {
            rosewellViewModel.deleteTransaction(transaction,yeahWeek,memberId)
            Toast.makeText(requireContext(),"Transaction DELETED Successfully",Toast.LENGTH_LONG).show()
            confirmTransactionDeleteDialog.dismiss()
        }
        confirmTransactionDeleteDialog.show()

    }


}