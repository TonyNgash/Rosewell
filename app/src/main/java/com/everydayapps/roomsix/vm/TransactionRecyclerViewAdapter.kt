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
        var transactionDate : TextView = view.findViewById(R.id.transactionDate)
        var transactionTime : TextView = view.findViewById(R.id.transactionTime)
        var transactionName : TextView = view.findViewById(R.id.transactionName)
        var transactionPrice : TextView = view.findViewById(R.id.transactionPrice)
        var paymentMethod : TextView = view.findViewById(R.id.paymentMethod)



        fun bind(data: Transaction){
            transactionDate.text = data.longDate
            transactionTime.text = data.time
            transactionName.text = data.service
            transactionPrice.text = "Kshs.${data.price}/-"
            paymentMethod.text = data.payment_method
        }

    }
}