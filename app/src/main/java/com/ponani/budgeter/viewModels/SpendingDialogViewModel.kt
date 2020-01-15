package com.ponani.budgeter.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ponani.budgeter.database.SpendingDatabase
import com.ponani.budgeter.database.SpendingItem
import com.ponani.budgeter.database.SpendingRepo
import kotlinx.coroutines.launch

class SpendingDialogViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: SpendingRepo
    val spendingItem : MutableLiveData<SpendingItem> = MutableLiveData()

    init {
        val spendingDAO = SpendingDatabase.getDatabase(application,viewModelScope).spendingDAO()
        repo = SpendingRepo(spendingDAO)

    }

    fun insertSpendingItem(item: SpendingItem) = viewModelScope.launch {
        repo.insertSpendingItem(item)
    }

    fun loadSpendingItem(itemID : Int) = viewModelScope.launch {
        val item : SpendingItem = repo.getSpendingItemByID(itemID)
        spendingItem.postValue(item)
    }
}