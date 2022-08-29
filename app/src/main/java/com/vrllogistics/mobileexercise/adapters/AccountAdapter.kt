package com.vrllogistics.mobileexercise.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vrllogistics.mobileexercise.R
import com.vrllogistics.mobileexercise.dataclasses.AccountsData
import com.vrllogistics.mobileexercise.utils.OnItemClickListener

class AccountAdapter(context: Context, accountsList: ArrayList<AccountsData>): RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    private var context: Context = context
    private var accountsList: ArrayList<AccountsData> = accountsList
    private var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {

        var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view: View = inflater.inflate(R.layout.layout_recyclerview_v_row, parent, false)

        return AccountViewHolder(view)
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        var accountsData = accountsList[position]
        holder.textViewNameOrTitle.text = "Account Name: ${accountsData.name}"
        holder.textViewBalance.text = "Balance: ${accountsData.balance}"
        holder.relativeLayoutParent.setOnClickListener { _ ->
            this.itemClickListener!!.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return accountsList.size
    }

    class AccountViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var relativeLayoutParent: RelativeLayout = itemView.findViewById(R.id.relativeLayoutParent)
        var textViewNameOrTitle: TextView = itemView.findViewById(R.id.textViewNameOrTitle)
        var textViewBalance: TextView = itemView.findViewById(R.id.textViewBalance)
    }
}