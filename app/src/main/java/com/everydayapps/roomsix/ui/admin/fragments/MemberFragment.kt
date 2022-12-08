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
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.everydayapps.roomsix.R
import com.everydayapps.roomsix.db.Member
import com.everydayapps.roomsix.vm.AdminMemberRecyclerAdapter
import com.everydayapps.roomsix.vm.RosewellViewModel

class MemberFragment : Fragment(),AdminMemberRecyclerAdapter.RowClickListener {

    lateinit var adminMemberRecyclerView: RecyclerView
    lateinit var adminRecyclerViewAdapter: AdminMemberRecyclerAdapter
    lateinit var rosewellViewModel: RosewellViewModel

    lateinit var saveMemberBtn : Button
    lateinit var firstName: EditText
    lateinit var lastName: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_member, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adminMemberRecyclerView = view.findViewById(R.id.adminMemberRecyclerView)

        adminMemberRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adminRecyclerViewAdapter = AdminMemberRecyclerAdapter(this@MemberFragment)
            adapter = adminRecyclerViewAdapter

        }
        rosewellViewModel = ViewModelProvider(this).get(RosewellViewModel::class.java)
        rosewellViewModel.getAllMembersObservers().observe(viewLifecycleOwner, Observer {
            adminRecyclerViewAdapter.setListData(ArrayList(it))
            adminRecyclerViewAdapter.notifyDataSetChanged()
        })
        rosewellViewModel.getAllMembers()

        firstName = view.findViewById(R.id.firstName)
        lastName = view.findViewById(R.id.lastName)
        saveMemberBtn = view.findViewById(R.id.saveMemberBtn)
        saveMemberBtn.setOnClickListener{

            val fname = firstName.text.toString()
            val lname = lastName.text.toString()

            if(fname.isNotEmpty() && lname.isNotEmpty()){
                if(saveMemberBtn.text.equals("Add")){
                    println("-------------SAVE IS ON-----------------")
                    val member = Member(0, fname,lname)
                    rosewellViewModel.insertMember(member)
                }else{
                    println("-------------UPDATE IS ON-----------------")
                    val member = Member(firstName.getTag(firstName.id).toString().toInt(),fname,lname)
                    rosewellViewModel.updateMember(member)
                    saveMemberBtn.setText("Add")
                    firstName.setTag(firstName.id, 0)
                }
            }else{
                Toast.makeText(context,"You must enter both names",Toast.LENGTH_LONG).show()
            }
            firstName.setText("")
            lastName.setText("")

        }
    }

    override fun onDeleteMemberClickListener(member: Member) {
        rosewellViewModel.deleteMember(member)
    }

    override fun onMemberClickListerner(member: Member) {
        firstName.setText(member.firstName)
        lastName.setText(member.lastName)
        firstName.setTag(firstName.id, member.memberId)
        saveMemberBtn.setText("Update")
    }


}