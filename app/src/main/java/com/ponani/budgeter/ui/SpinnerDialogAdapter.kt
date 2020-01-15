package com.ponani.budgeter.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ponani.budgeter.R

class SpinnerDialogAdapter(
    val context: Context, var spendingType: Array<String>,
    var resource: IntArray
) :
    BaseAdapter() {

    val inflater = LayoutInflater.from(context)

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View
        val viewHolder : ItemRowHolder
        if (p1 == null) {
            view  = inflater.inflate(R.layout.spinner_spending_row, p2,false)
            viewHolder = ItemRowHolder(view)
            view?.tag = viewHolder
        }else{
            view = p1
            viewHolder = view.tag as ItemRowHolder
        }

        viewHolder.textView.text = spendingType.get(p0)
        viewHolder.imageView.setImageResource(resource.get(p0))

        return view
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
      return 0
    }

    override fun getCount(): Int {
        return spendingType.size
    }

    private class ItemRowHolder(row: View?) {

        val textView: TextView
        val imageView: ImageView

        init {
            this.textView = row?.findViewById(R.id.spinnerSpendingItem) as TextView
            this.imageView = row?.findViewById(R.id.spinnerImage) as ImageView
        }
    }

}