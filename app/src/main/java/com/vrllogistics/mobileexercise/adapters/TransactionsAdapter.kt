package com.vrllogistics.mobileexercise.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vrllogistics.mobileexercise.R
import com.vrllogistics.mobileexercise.dataclasses.TransactionData
import com.vrllogistics.mobileexercise.utils.OnItemClickListener

class TransactionsAdapter(context: Context, transactionsList: ArrayList<TransactionData>): RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder>() {

    private var context: Context = context
    private var transactionsList: ArrayList<TransactionData> = transactionsList
    private var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view: View = inflater.inflate(R.layout.layout_recyclerview_v_row, parent, false)
        return TransactionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        var transactionData = transactionsList[position]
        holder.textViewNameOrTitle.text = "Narration: ${transactionData.title}"
        holder.textViewBalance.text = "Balance: ${transactionData.balance}"
    }

    override fun getItemCount(): Int {
        return this.transactionsList.size
    }

    class TransactionsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var relativeLayoutParent: RelativeLayout = itemView.findViewById(R.id.relativeLayoutParent)
        var textViewNameOrTitle: TextView = itemView.findViewById(R.id.textViewNameOrTitle)
        var textViewBalance: TextView = itemView.findViewById(R.id.textViewBalance)
    }
}