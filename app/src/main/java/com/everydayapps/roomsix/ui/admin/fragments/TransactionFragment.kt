package com.everydayapps.roomsix.ui.admin.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.everydayapps.roomsix.R
import com.everydayapps.roomsix.vm.PublicMemberAdapter
import com.everydayapps.roomsix.vm.RosewellViewModel
import com.everydayapps.roomsix.vm.SingletonTransaction
import com.everydayapps.roomsix.vm.TransactionRecyclerViewAdapter


class TransactionFragment : Fragment() {

    lateinit var transactionRecyclerView: RecyclerView
    lateinit var transactionRecyclerViewAdapter: TransactionRecyclerViewAdapter
    lateinit var rosewellViewModel: RosewellViewModel
    lateinit var myContext : Context

    lateinit var transactionViewMemberName: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transactionViewMemberName = view.findViewById(R.id.transactionViewMemberName)
        transactionViewMemberName.text = "All Transactions By: "+SingletonTransaction.getMemberTransactionFullname()

        transactionRecyclerView = view.findViewById(R.id.transactionRecyclerView)

        transactionRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            transactionRecyclerViewAdapter = TransactionRecyclerViewAdapter()
            adapter = transactionRecyclerViewAdapter
        }
        rosewellViewModel = ViewModelProvider(this).get(RosewellViewModel::class.java)
        rosewellViewModel.getAllMemberTransactionsObervers().observe(viewLifecycleOwner, Observer {
            transactionRecyclerViewAdapter.setListData(ArrayList(it))
            transactionRecyclerViewAdapter.notifyDataSetChanged()
        })
        val memberId = SingletonTransaction.getTmid()
        rosewellViewModel.getMemberTransactions(memberId)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Toast.makeText(requireContext(),"Back Pressed",Toast.LENGTH_LONG).show()
            }

        })
    }


}