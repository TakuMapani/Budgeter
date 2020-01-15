package com.ponani.budgeter.Utilities

import com.ponani.budgeter.R
import com.ponani.budgeter.database.SpendingItem

object Constants {

    val DIALOG_DATA_ID : String = "id"

    val UNDODELETE : String = "undo"
    val RE_ADD_SPENDING: Int = 12
    val RE_ADD_SPENDING_STR : String = "readd"

    //predefined spending categories that will be used in creating the fragment
    val SPENDING_CATEGORY_LIST : Array<String> = arrayOf("Bills","Groceries","Food","Tech","Clothes")
    //array of images for the spending categories.
    val SPENDING_IMAGE : IntArray = intArrayOf(
        R.drawable.ic_bill,
        R.drawable.ic_shopping,
        R.drawable.ic__food,
        R.drawable.ic_electronics,
        R.drawable.ic_clothes
    )

    //Storing Temporary spending item to be deleted later
    var TEMP_ITEM : SpendingItem = SampleData.getSampleData()!!.get(1)
}