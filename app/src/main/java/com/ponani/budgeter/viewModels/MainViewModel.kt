package com.ponani.budgeter.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ponani.budgeter.database.SpendingDatabase
import com.ponani.budgeter.database.SpendingItem
import com.ponani.budgeter.database.SpendingRepo
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repo : SpendingRepo
    val spendingList : LiveData<List<SpendingItem>>

    init {
        //Get a reference to the DAO which is used to initialise the repo
        val spendingDAO = SpendingDatabase.getDatabase(application, viewModelScope).spendingDAO()
        repo = SpendingRepo(spendingDAO)
        spendingList = repo.spendingList
    }

    /**
     * Async Task for Kotlin is covered by corutines ie Suspend fun
     * To use these background functions, they need a scope and view model comes equiped with scope
     */
    fun deleteAll() = viewModelScope.launch {
        repo.deleteSpendingTable()
    }

    fun insertSampleData() = viewModelScope.launch {
        repo.insertSampleData()
    }
}