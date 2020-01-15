package com.ponani.budgeter.Utilities

import com.ponani.budgeter.R

object Constants {
    val DIALOG_DATA_ID : String = "id"

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
}