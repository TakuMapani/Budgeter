package com.ponani.budgeter.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

/**
 * The class is using BigDecimal to store currency
 * Due to using unsupported data types (Date, BigDecimal) type converters are created
 * The values are intialised ie spendingID: Int = 0 to prevent errors from the constructor
 */
@Entity(tableName = "spending_table")
class SpendingItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "spending_id")
    var spendingID: Int = 0,
    var spendingDate: Date = Date(0),
    var spendingCategory: Int = 0,
    var spendingDescription : String = "",
    var spendingAmount : BigDecimal = BigDecimal.ZERO
) {
    constructor(_spendingDate: Date,_spendingCategory: Int,_spendingDescription: String,_spendingAmount: BigDecimal) : this() {
        spendingDate = _spendingDate
        spendingCategory = _spendingCategory
        spendingDescription = _spendingDescription
        spendingAmount = _spendingAmount
    }


}