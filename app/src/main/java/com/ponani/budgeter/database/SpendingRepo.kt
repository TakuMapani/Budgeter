package com.ponani.budgeter.database

import androidx.lifecycle.LiveData
import com.ponani.budgeter.Utilities.SampleData
import java.math.BigDecimal

class SpendingRepo (private val spendingDAO: SpendingDAO){

    val spendingList : LiveData<List<SpendingItem>> = spendingDAO.getSpendingList()
    val total : LiveData<BigDecimal> = spendingDAO.getTotal()

    //TODO: fix getSpendingByID
    /*fun  getSpendingByID(id: Int){
        return spendingDAO.getSpendingByID(id)
    }*/

    //TODO: remove function and place in fragment
    suspend fun  insertSpendingItem(spendingItem: SpendingItem){
        spendingDAO.insertSpendingItem(spendingItem)
    }

    suspend fun deleteSpendingItem(spendingItem: SpendingItem){
        spendingDAO.deleteSpendingItem(spendingItem)
    }

    suspend fun deleteSpendingTable(){
        spendingDAO.deleteSpendingTable()
    }

    suspend fun insertSampleData(){
        spendingDAO.insertSampleData(SampleData.getSampleData())
    }
}