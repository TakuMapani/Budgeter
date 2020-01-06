package com.ponani.budgeter.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SpendingDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpendingItem(spendingItem: SpendingItem)

    @Insert
    suspend fun insertSampleData(spendingItemList: MutableList<SpendingItem>?)

    @Query("SELECT * FROM SPENDING_TABLE")
    fun getSpendingList(): LiveData<List<SpendingItem>>

    @Delete
    suspend fun deleteSpendingItem(spendingItem: SpendingItem)

    @Query("DELETE FROM SPENDING_TABLE")
    suspend fun deleteSpendingTable()
}