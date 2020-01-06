package com.ponani.budgeter.Utilities

import com.ponani.budgeter.database.SpendingItem
import java.math.BigDecimal
import java.util.*

object SampleData {

    fun getSampleData(): MutableList<SpendingItem>? {
        var spendingItems : MutableList<SpendingItem> = mutableListOf()
        var calendar : Calendar = Calendar.getInstance()
        var date : Date = calendar.getTime()
        spendingItems.add(SpendingItem(date,3,"Fish and chips", BigDecimal.valueOf(10.40)))
        spendingItems.add(SpendingItem(date,2,"BigW shoes", BigDecimal.valueOf(25)))
        spendingItems.add(SpendingItem(date,1,"Showering Liquid", BigDecimal.valueOf(7)))
        return spendingItems;

    }
}