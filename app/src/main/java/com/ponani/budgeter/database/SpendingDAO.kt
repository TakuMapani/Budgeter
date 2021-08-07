package com.ponani.budgeter.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.math.BigDecimal

@Dao
interface SpendingDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpendingItem(spendingItem: SpendingItem)

    @Insert
    suspend fun insertSampleData(spendingItemList: MutableList<SpendingItem>?)

    @Query("SELECT * FROM SPENDING_TABLE")
    fun getSpendingList(): LiveData<List<SpendingItem>>

    @Query("SELECT * FROM SPENDING_TABLE WHERE spending_id=:id")
    suspend fun getSpendingItemByID(id : Int): SpendingItem

    @Query("SELECT COALESCE(SUM(COALESCE(spendingAmount,0)),0) FROM spending_table")
    fun getTotal(): LiveData<BigDecimal>

    @Delete
    suspend fun deleteSpendingItem(spendingItem: SpendingItem)

    @Query("DELETE FROM SPENDING_TABLE")
    suspend fun deleteSpendingTable()
}