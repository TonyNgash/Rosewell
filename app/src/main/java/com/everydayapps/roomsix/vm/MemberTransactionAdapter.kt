package com.everydayapps.roomsix.vm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.everydayapps.roomsix.R
import com.everydayapps.roomsix.db.Member

class MemberTransactionAdapter(val listener: MemberTransactionAdapter.MemberTransactionRowClickListener, val myView: View):RecyclerView.Adapter<MemberTransactionAdapter.MemberTransactionViewHolder>() {

    var items = ArrayList<Member>()

    fun setListData(data: ArrayList<Member>){
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): MemberTransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.admin_member_transaction_item,parent,false)
        return MemberTransactionViewHolder(inflater, listener)
    }

    override fun onBindViewHolder(holder: MemberTransactionViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            listener.onMemberTransactionListener(items[position],myView)
        }
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
    class MemberTransactionViewHolder(view: View,val listener: MemberTransactionRowClickListener):RecyclerView.ViewHolder(view){
        var publicMemberName: TextView = view.findViewById(R.id.member_transaction_name)

        fun bind(data: Member){
            val displayText = "${data.firstName} ${data.lastName}"
            publicMemberName.text = displayText
        }
    }

    interface MemberTransactionRowClickListener{
        fun onMemberTransactionListener(member: Member,myView: View)
    }


}