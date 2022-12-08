package com.everydayapps.roomsix.vm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.everydayapps.roomsix.R
import com.everydayapps.roomsix.db.Member

class PublicMemberAdapter(val listener: PublicRowClickListener,val myView: View):RecyclerView.Adapter<PublicMemberAdapter.PublicMemberViewHolder>() {

    var items = ArrayList<Member>()

    fun setListData(data: ArrayList<Member>){
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicMemberAdapter.PublicMemberViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.public_single_member_item,parent,false)
        return PublicMemberViewHolder(inflater,listener)
    }

    override fun onBindViewHolder(holder: PublicMemberAdapter.PublicMemberViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            listener.onPublicMemberListener(items[position],myView)
        }
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PublicMemberViewHolder(view: View,val listener: PublicRowClickListener):RecyclerView.ViewHolder(view){
        var publicMemberName: TextView = view.findViewById(R.id.admin_member_name)

        fun bind(data: Member){
            val displayText = "${data.firstName} ${data.lastName}"
            publicMemberName.text = displayText
        }
    }

    interface PublicRowClickListener{
        fun onPublicMemberListener(member: Member,myView: View)
    }


}