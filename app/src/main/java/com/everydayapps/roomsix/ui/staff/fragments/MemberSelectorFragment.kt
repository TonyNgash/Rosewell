package com.everydayapps.roomsix.ui.staff.fragments

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
import com.everydayapps.roomsix.vm.TransactionManager


class MemberSelectorFragment : Fragment(), PublicMemberAdapter.PublicRowClickListener {

    lateinit var publicMemberRecyclerView: RecyclerView
    lateinit var publicRecyclerViewAdapter: PublicMemberAdapter
    lateinit var rosewellViewModel: RosewellViewModel
    lateinit var myContext : Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_member_selector, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        publicMemberRecyclerView = view.findViewById(R.id.memberSelectorRecyclerView)

        publicMemberRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            publicRecyclerViewAdapter = PublicMemberAdapter(this@MemberSelectorFragment,view)
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

        SingletonTransaction.initObj(requireContext())
        SingletonTransaction.recordMember(member)

        Navigation.findNavController(myView).navigate(R.id.action_memberSelectorFragment_to_serviceSelectorFragment)
    }


}