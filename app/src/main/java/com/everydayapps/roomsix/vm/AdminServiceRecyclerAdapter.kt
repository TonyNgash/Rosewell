package com.everydayapps.roomsix.vm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.everydayapps.roomsix.R
import com.everydayapps.roomsix.db.Service

class AdminServiceRecyclerAdapter(val listener: ServiceRowClickListener): RecyclerView.Adapter<AdminServiceRecyclerAdapter.AdminServiceViewHolder>() {

    var items = ArrayList<Service>()

    fun setListData(data: ArrayList<Service>){
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): AdminServiceRecyclerAdapter.AdminServiceViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.admin_single_service_item,parent,false)
        return AdminServiceViewHolder(inflater, listener)
    }

    override fun onBindViewHolder(holder: AdminServiceRecyclerAdapter.AdminServiceViewHolder,position: Int) {
        holder.itemView.setOnClickListener{
            listener.onServiceClickListener(items[position])
        }
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class AdminServiceViewHolder(view: View, val listener: ServiceRowClickListener) : RecyclerView.ViewHolder(view){
        var adminServiceTitle: TextView = view.findViewById(R.id.admin_service_title)
        val deleteServiceBtn : ImageView = view.findViewById(R.id.deleteServiceBtn)
        fun bind(data: Service){
            val displayText = "${data.serviceTitle} Kshs. ${data.servicePrice}/-"
            adminServiceTitle.text = displayText

            deleteServiceBtn.setOnClickListener {
                listener.onDeleteServiceClickListener(data)
            }
        }
    }
    interface ServiceRowClickListener{
        fun onDeleteServiceClickListener(service: Service)
        fun onServiceClickListener(service: Service)
    }

}