package com.everydayapps.roomsix.vm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.everydayapps.roomsix.R
import com.everydayapps.roomsix.db.Transaction

class WeeklyRecyclerAdapter(val listener: WeeklyRowClickListener): RecyclerView.Adapter<WeeklyRecyclerAdapter.WeeklyViewHolder>() {

    var items = ArrayList<Transaction>()

    fun setListData(data: ArrayList<Transaction>){
        this.items = data
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.weekly_transaction_item,parent,false)
        return WeeklyViewHolder(inflater,listener)
    }

    override fun onBindViewHolder(holder: WeeklyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class WeeklyViewHolder(view: View,val listener: WeeklyRowClickListener): RecyclerView.ViewHolder(view){
        var transactionDate : TextView = view.findViewById(R.id.transactionDate)
        var transactionTime : TextView = view.findViewById(R.id.transactionTime)
        var transactionName : TextView = view.findViewById(R.id.transactionName)
        var transactionPrice : TextView = view.findViewById(R.id.transactionPrice)
        var paymentMethod : TextView = view.findViewById(R.id.paymentMethod)
        var deleteTransactionBtn : ImageView = view.findViewById(R.id.deleteTransactionBtn)

        fun bind(data: Transaction){
            transactionDate.text = data.longDate
            transactionTime.text = data.time
            transactionName.text = data.service
            transactionPrice.text = "Kshs.${data.price}/-"
            paymentMethod.text = data.payment_method

            deleteTransactionBtn.setOnClickListener {
                listener.onDeleteWeeklyTransaction(data)
            }
        }
    }
    interface WeeklyRowClickListener{
        fun onDeleteWeeklyTransaction(transaction: Transaction)
    }
}