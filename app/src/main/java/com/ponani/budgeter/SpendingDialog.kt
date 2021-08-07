package com.ponani.budgeter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ponani.budgeter.Utilities.Constants
import com.ponani.budgeter.ui.SpinnerDialogAdapter
import com.ponani.budgeter.viewModels.SpendingDialogViewModel

class SpendingDialog : DialogFragment() {

    var bAdd: Button? = null
    var bCancel: Button? = null
    var spendingAmount: EditText? = null
    var spendingDesc: EditText? = null
    var dialogHeading: TextView? = null
    var spendingType: Spinner? = null

    private lateinit var viewModel: SpendingDialogViewModel

    //create an instance of dialog fragment
    fun newInstance(): SpendingDialog {
        var fragment: SpendingDialog = SpendingDialog()
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.dialog_spending, container, false)

        bAdd = rootView.findViewById<Button>(R.id.addButton)
        bCancel = rootView.findViewById<Button>(R.id.cancelButton)
        spendingAmount = rootView.findViewById<EditText>(R.id.edDialogAmount)
        spendingDesc = rootView.findViewById<EditText>(R.id.edDialogDescription)
        spendingType = rootView.findViewById<Spinner>(R.id.spinnerDialogType)
        dialogHeading = rootView.findViewById<TextView>(R.id.tvDialogHeading)


        var adapter: SpinnerDialogAdapter = SpinnerDialogAdapter(
            activity!!.applicationContext,
            Constants.SPENDING_CATEGORY_LIST,
            Constants.SPENDING_IMAGE
        )

        if (spendingType != null) {
            spendingType!!.adapter = adapter
        }

        initViewModel()

        /**
         * Set Button actions for adding a spending item or cancelling
         */
        this.bAdd!!.setOnClickListener {
            saveAndContinue()
            dismiss()
        }

        this.bCancel!!.setOnClickListener {
            dismiss()
        }

        return rootView

    }

    /**
     * Function to save spendingItem to the database
     */
    private fun saveAndContinue() {
        viewModel.insertSpendingItem(
            spendingDesc?.text.toString(), spendingAmount?.text.toString(),
            spendingType!!.selectedItemPosition
        )
    }

    private fun initViewModel() {
        //Initialise spending View Model
        viewModel = ViewModelProviders.of(this).get(SpendingDialogViewModel::class.java)

        /**
         * When spending item is loaded, it populates views with text from existing spending item
         */
        viewModel.spendingItem.observe(this, Observer { item ->
            spendingAmount?.setText(item.spendingAmount.toString())
            spendingDesc?.setText(item.spendingDescription.toString())
            spendingType?.setSelection(item.spendingCategory)
        })

        /**
         * Check the arguments from main activity to see if it a new spending Item or an existing item
         * If existing item, load spending item from viewModel and set Text for the different views
         */
        if (arguments != null) run {
            val itemID: Int = arguments!!.getInt(Constants.DIALOG_DATA_ID)
            viewModel.loadSpendingItem(itemID)
            dialogHeading?.setText("Edit Spending")
        } else {
            dialogHeading?.text = "Enter Expense"
        }
    }

    /**
     * Change dialog width percantage
     */
    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }


}
