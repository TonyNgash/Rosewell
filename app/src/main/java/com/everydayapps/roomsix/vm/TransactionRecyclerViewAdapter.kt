package com.everydayapps.roomsix.vm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.everydayapps.roomsix.R
import com.everydayapps.roomsix.db.Transaction

class TransactionRecyclerViewAdapter: RecyclerView.Adapter<TransactionRecyclerViewAdapter.TransactionViewHolder>() {

    var items = ArrayList<Transaction>()

    fun setListData(data : ArrayList<Transaction>){
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.single_transaction_item,parent,false)
//        return MemberTransactionAdapter.MemberTransactionViewHolder(inflater, listener)
        return TransactionViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
//        holder.itemView.setOnClickListener{
//            listener.onMemberTransactionListener(items[position],myView)
//        }
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var transactionName : TextView = view.findViewById(R.id.transactionName)

        fun bind(data: Transaction){
            val displayText = "${data.fullname} - ${data.service} - Kshs.${data.price} on:${data.date} @:${data.time}"
            transactionName.text = displayText
        }

    }



}