package com.ponani.budgeter.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.ponani.budgeter.R
import com.ponani.budgeter.Utilities.Constants
import com.ponani.budgeter.database.SpendingItem
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat

class SpendingListAdapter internal constructor(
    context: Context,
    private val listener : OnItemClickListener
) : RecyclerView.Adapter<SpendingListAdapter.SpendingViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var spendingItemList = emptyList<SpendingItem>().toMutableList()
    var total: BigDecimal? = BigDecimal(1000) //this will need to be fixed and should start as null
    val mContext: Context = context

    lateinit var tempItem: SpendingItem
    var tempItemPosition: Int = 0  //to be used later on

    //initialising the array that holds category colors
    private val spendingCategoryColorPicker: TypedArray =
        context.resources.obtainTypedArray(R.array.spendingColours)


    inner class SpendingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        val tvSDescription = itemView.findViewById<TextView?>(R.id.tvSpendingItem)
        val tvSDate = itemView.findViewById<TextView?>(R.id.tvSpendingDate)
        val fabSpendingItem: FloatingActionButton = itemView.findViewById(R.id.FABSpendingItem)
        val pieChart: PieChart = itemView.findViewById(R.id.pieChartSpending)
        val tvSAmount: TextView = itemView.findViewById(R.id.tvAmount)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(spendingItemList[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpendingViewHolder {
        //To change body of created functions use File | Settings | File Templates.
        val itemView = inflater.inflate(R.layout.spending_list_item, parent, false)
        return SpendingViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        //To change body of created functions use File | Settings | File Templates.
        return spendingItemList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SpendingViewHolder, position: Int) {
        //To change body of created functions use File | Settings | File Templates.
        val current = spendingItemList[position]
        //Set description text
        holder.tvSDescription?.text = current.spendingDescription
        //date format
        val dateFormat = SimpleDateFormat("EEE hh:mm dd/MM/yyyy")
        holder.tvSDate?.text = dateFormat.format(current.spendingDate)
        //Set Spending Amount
        holder.tvSAmount?.text = "$" + current.spendingAmount
        //fab spending item
        val spendingColour = spendingCategoryColorPicker.getColor(current.spendingCategory, 0)
        holder.fabSpendingItem.backgroundTintList = ColorStateList.valueOf(spendingColour)
        holder.fabSpendingItem.isEnabled = false
        holder.fabSpendingItem.setImageResource(Constants.SPENDING_IMAGE[current.spendingCategory])

        val color: Int = spendingCategoryColorPicker.getColor(current.spendingCategory, 0)

        /**
         * Spending Item graph section
         */
        //spending value for spending item
        var value: Float = current.spendingAmount.setScale(2, RoundingMode.DOWN).toFloat()
        //total spent amount - value of item
        var reminderBD = total!!.subtract(current.spendingAmount)
        var reminder: Float = reminderBD.setScale(2, RoundingMode.DOWN).toFloat()

        var values: MutableList<PieEntry> = ArrayList()
        values.add(PieEntry(value, "ONE"))
        values.add(PieEntry(reminder, "THREE"))

        val dataSet: PieDataSet = PieDataSet(values, "Numbers")
        val data = PieData(dataSet)
        data.setDrawValues(false)//remove values for segments in piechart
        holder.pieChart.data = data
        holder.pieChart.holeRadius = 70f
        holder.pieChart.setDrawCenterText(false)
        holder.pieChart.setDrawEntryLabels(false)
        holder.pieChart.description.isEnabled = false //remove description text box
        holder.pieChart.legend.isEnabled = false //remove legend
        dataSet.setColors(color, ContextCompat.getColor(mContext, R.color.graphGrey))
    }

    internal fun setSpending(spendingItemList: MutableList<SpendingItem>) {
        this.spendingItemList = spendingItemList
        notifyDataSetChanged()
    }

    /**
     * The function is used to delete item by swipping from the view only
     * Returns deleted item which will be removed from the database by the viewHolder/Repo
     * tempPosition variable used in case of undo,
     */
    fun deleteItem(position: Int,rv: RecyclerView): SpendingItem {
        tempItem = spendingItemList.get(position)
        Constants.TEMP_ITEM = tempItem
        tempItemPosition = position
        spendingItemList.removeAt(position)
        notifyItemRemoved(position)
        showUndoSnackBar(rv)
        return tempItem
    }

    /**
     * Undo swipe delete of a spending Item
     * Notifies RecyclerView of data change
     * Broadcasts on app to undo the delete, received in mainActivity
     */
    fun undoDelete() {
        val intent: Intent = Intent(Constants.UNDODELETE)
        intent.putExtra(Constants.RE_ADD_SPENDING_STR, Constants.RE_ADD_SPENDING)
        spendingItemList.add(tempItemPosition, tempItem)
        notifyItemInserted(tempItemPosition)
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent)
    }

    //Shows undo snack bar when item is deleted
    fun showUndoSnackBar(rv : RecyclerView){
        val snackbar : Snackbar = Snackbar.make(
            rv,"Undo", Snackbar.LENGTH_LONG
        )

        snackbar.setAction("Undo",View.OnClickListener {
            undoDelete()
        })
        snackbar.show()
    }

    /**
     * Function to set total spent amount
     */
    fun setSpendingTotal(total: BigDecimal?) {
        this.total = total
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun onItemClick(item : SpendingItem)
    }
}