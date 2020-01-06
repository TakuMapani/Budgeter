package com.ponani.budgeter.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ponani.budgeter.R
import com.ponani.budgeter.Utilities.Constants
import com.ponani.budgeter.database.SpendingItem

class SpendingListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<SpendingListAdapter.SpendingViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var spendingItemList = emptyList<SpendingItem>()


    inner class SpendingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvSDescription = itemView.findViewById<TextView?>(R.id.tvSpendingItem)
        //tvSAmount = itemView.findViewById(R.id.tvSpendingAmount);
        //tvSAmount = itemView.findViewById(R.id.tvSpendingAmount);
        val tvSTtype = itemView.findViewById<TextView?>(R.id.tvSpendingType)
        val tvSDate = itemView.findViewById<TextView?>(R.id.tvSpendingDate)
        val fabSpendingItem: FloatingActionButton = itemView.findViewById(R.id.FABSpendingItem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpendingViewHolder {
        //To change body of created functions use File | Settings | File Templates.
        val itemView = inflater.inflate(R.layout.spending_list_item,parent,false)
        return SpendingViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        //To change body of created functions use File | Settings | File Templates.
        return spendingItemList.size
    }

    override fun onBindViewHolder(holder: SpendingViewHolder, position: Int) {
         //To change body of created functions use File | Settings | File Templates.
        val current = spendingItemList[position]
        holder.tvSDescription?.setText(current.spendingDescription + "$" + current.spendingAmount)
        holder.tvSDate?.setText(current.spendingDate.toString())
        holder.tvSTtype?.setText("place holder")
        holder.fabSpendingItem.isEnabled = false
        holder.fabSpendingItem.setImageResource(Constants.SPENDING_IMAGE.get(current.spendingCategory))


    }

    internal fun setSpending(spendingItemList:List<SpendingItem>){
        this.spendingItemList = spendingItemList
        notifyDataSetChanged()
    }
}