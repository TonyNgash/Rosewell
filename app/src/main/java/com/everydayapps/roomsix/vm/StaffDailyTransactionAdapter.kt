package com.everydayapps.roomsix.vm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.everydayapps.roomsix.R
import com.everydayapps.roomsix.db.Transaction
import org.w3c.dom.Text

class StaffDailyTransactionAdapter: RecyclerView.Adapter<StaffDailyTransactionAdapter.StaffDailyViewHolder>(){

    var items = ArrayList<Transaction>()

    fun setListData(data: ArrayList<Transaction>){
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffDailyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.staff_daily_transaction_item,parent,false)
        return StaffDailyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: StaffDailyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
    class StaffDailyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var transactionName : TextView = view.findViewById(R.id.transactionName)
        var transactionPrice : TextView = view.findViewById(R.id.transactionPrice)
        var paymentMethod : TextView = view.findViewById(R.id.paymentMethod)
        var transactionTime: TextView = view.findViewById(R.id.transactionTime)
        fun bind(data: Transaction){
            transactionName.text = data.service
            transactionPrice.text = "Kshs.${data.price}"
            paymentMethod.text = data.payment_method
            transactionTime.text = data.time
        }

    }
}