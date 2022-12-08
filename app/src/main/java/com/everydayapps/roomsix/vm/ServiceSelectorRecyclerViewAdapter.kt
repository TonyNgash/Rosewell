package com.everydayapps.roomsix.vm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.everydayapps.roomsix.R
import com.everydayapps.roomsix.db.Service

class ServiceSelectorRecyclerViewAdapter(val listener: ServiceSelectorClickListener,val myView: View):RecyclerView.Adapter<ServiceSelectorRecyclerViewAdapter.SerivceSelectorViewHolder>(){

    var items = ArrayList<Service>()

    fun setListData(data: ArrayList<Service>){
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerivceSelectorViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.service_selector_item, parent,false)
        return SerivceSelectorViewHolder(inflater,listener)
    }

    override fun onBindViewHolder(holder: SerivceSelectorViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            listener.onServiceSelectorListener(items[position], myView )
        }
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class SerivceSelectorViewHolder(view: View, listener: ServiceSelectorClickListener): RecyclerView.ViewHolder(view){
        var serviceSelectorName: TextView = view.findViewById(R.id.service_selector_name)

        fun bind(data: Service){
            val displayText = "${data.serviceTitle} ${data.servicePrice}"
            serviceSelectorName.text = displayText
        }
    }
    interface ServiceSelectorClickListener{
        fun onServiceSelectorListener(service: Service,myView: View)
    }
}