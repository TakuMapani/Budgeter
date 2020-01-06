package com.ponani.budgeter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(SpendingItem::class), version = 1,exportSchema = false)
@TypeConverters(DateTypeConverter::class,CurrencyTypeConverter::class)
public abstract class SpendingDatabase: RoomDatabase(){

    abstract fun spendingDAO():SpendingDAO

    companion object{

        @Volatile
        private var INSTANCE: SpendingDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): SpendingDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SpendingDatabase::class.java,
                    "spending_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}