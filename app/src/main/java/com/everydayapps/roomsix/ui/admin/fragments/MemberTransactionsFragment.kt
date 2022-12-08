package com.everydayapps.roomsix.ui.admin.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.everydayapps.roomsix.R
import com.everydayapps.roomsix.db.Member
import com.everydayapps.roomsix.vm.PublicMemberAdapter
import com.everydayapps.roomsix.vm.RosewellViewModel
import com.everydayapps.roomsix.vm.SingletonTransaction


class MemberTransactionsFragment : Fragment(), PublicMemberAdapter.PublicRowClickListener {

    lateinit var publicMemberRecyclerView: RecyclerView
    lateinit var publicRecyclerViewAdapter: PublicMemberAdapter
    lateinit var rosewellViewModel: RosewellViewModel
    lateinit var myContext : Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_member_transactions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        publicMemberRecyclerView = view.findViewById(R.id.memberTransactionRecyclerView)

        publicMemberRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            publicRecyclerViewAdapter = PublicMemberAdapter(this@MemberTransactionsFragment,view)
            adapter = publicRecyclerViewAdapter
        }
        rosewellViewModel = ViewModelProvider(this).get(RosewellViewModel::class.java)
        rosewellViewModel.getAllMembersObservers().observe(viewLifecycleOwner, Observer {
            publicRecyclerViewAdapter.setListData(ArrayList(it))
            publicRecyclerViewAdapter.notifyDataSetChanged()
        })
        rosewellViewModel.getAllMembers()
    }

    override fun onPublicMemberListener(member: Member,myView: View) {
        val name = "${member.firstName} ${member.lastName}"
        Toast.makeText(context,"You clicked on $name", Toast.LENGTH_LONG).show()
        Navigation.findNavController(myView).navigate(R.id.action_memberTransactionsFragment_to_transactionFragment)
    }


}