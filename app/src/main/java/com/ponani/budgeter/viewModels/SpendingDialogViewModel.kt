package com.ponani.budgeter.viewModels

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ponani.budgeter.database.SpendingDatabase
import com.ponani.budgeter.database.SpendingItem
import com.ponani.budgeter.database.SpendingRepo
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.*

class SpendingDialogViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: SpendingRepo
    val spendingItem : MutableLiveData<SpendingItem> = MutableLiveData()

    init {
        val spendingDAO = SpendingDatabase.getDatabase(application,viewModelScope).spendingDAO()
        repo = SpendingRepo(spendingDAO)

    }

    fun insertSpendingItem(spendingDesc : String, spendingAmt : String, spendingCat : Int) {
        val calendar = Calendar.getInstance()
        val date = calendar.time
        var item = spendingItem.value

        if (item == null) {
            if(TextUtils.isEmpty(spendingDesc.trim()) || TextUtils.isEmpty(spendingAmt.trim())){
                return
            }
            item = SpendingItem(date,spendingCat,spendingDesc, BigDecimal.valueOf(spendingAmt.trim().toLong()))
        }else{
            if(TextUtils.isEmpty(spendingDesc.trim()) || TextUtils.isEmpty(spendingAmt.trim())){
                return
            }
            item.spendingAmount = BigDecimal.valueOf(spendingAmt.trim().toLong())
            item.spendingDescription = spendingDesc.trim()
            item.spendingCategory = spendingCat
        }

        addItemDB(item)
    }

    private fun addItemDB(item: SpendingItem) = viewModelScope.launch {
        repo.insertSpendingItem(item)
    }


    fun loadSpendingItem(itemID : Int) = viewModelScope.launch {
        val item : SpendingItem = repo.getSpendingItemByID(itemID)
        spendingItem.postValue(item)
    }
}