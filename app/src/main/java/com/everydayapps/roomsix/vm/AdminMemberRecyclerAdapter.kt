package com.everydayapps.roomsix.vm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.everydayapps.roomsix.R
import com.everydayapps.roomsix.db.Member

class AdminMemberRecyclerAdapter(val listener: RowClickListener): RecyclerView.Adapter<AdminMemberRecyclerAdapter.AdminMemberViewHolder>() {

    var items = ArrayList<Member>()

    fun setListData(data: ArrayList<Member>){
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminMemberRecyclerAdapter.AdminMemberViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.admin_single_member_item,parent,false)
        return AdminMemberViewHolder(inflater,listener)
    }

    override fun onBindViewHolder(holder: AdminMemberRecyclerAdapter.AdminMemberViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            listener.onMemberClickListerner(items[position])
        }
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
     return items.size
    }

    class AdminMemberViewHolder(view: View, val listener: RowClickListener):RecyclerView.ViewHolder(view){
        var adminMemberName: TextView = view.findViewById(R.id.admin_member_name)
        val deleteMemberBtn: ImageView = view.findViewById(R.id.deleteMemberBtn)
        fun bind(data: Member){
            val displayText = "${data.firstName} ${data.lastName}"
            adminMemberName.text = displayText

            deleteMemberBtn.setOnClickListener {
                listener.onDeleteMemberClickListener(data)
            }
        }
    }
    interface RowClickListener{
        fun onDeleteMemberClickListener(member: Member)
        fun onMemberClickListerner(member: Member)
    }

}