package com.ponani.budgeter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.ponani.budgeter.Utilities.Constants
import com.ponani.budgeter.ui.SpinnerDialogAdapter
import com.ponani.budgeter.viewModels.SpendingDialogViewModel

class SpendingDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView : View = inflater!!.inflate(R.layout.dialog_spending,container,false)

        val bAdd = rootView.findViewById<Button>(R.id.addButton)
        val bCancel = rootView.findViewById<Button>(R.id.cancelButton)
        val spendingAmount = rootView.findViewById<EditText>(R.id.edDialogAmount)
        val spendingDesc = rootView.findViewById<EditText>(R.id.edDialogDescription)
        val spendingType = rootView.findViewById<Spinner>(R.id.spinnerDialogType)
        val dialogHeading = rootView.findViewById<TextView>(R.id.tvDialogHeading)

        return super.onCreateView(inflater, container, savedInstanceState)

        var adapter : SpinnerDialogAdapter= SpinnerDialogAdapter(activity!!.applicationContext,Constants.SPENDING_CATEGORY_LIST, Constants.SPENDING_IMAGE)

        initViewModel()

    }

    private fun initViewModel() {
        //Initialise spending View Model
    }
}