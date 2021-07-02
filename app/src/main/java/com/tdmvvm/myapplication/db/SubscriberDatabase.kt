package com.tdmvvm.myapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Subscriber::class], version = 1)
abstract class SubscriberDatabase : RoomDatabase() {


    // we have only one Dao if you have multiple dao u can include iver here
    abstract val subscriberDao: SubscriberDao

    companion object {

        @Volatile  // This will make sure that if the INSTANCE varible is assigned
        // Then every thread get the update
        private var INSTANCE: SubscriberDatabase? = null

        @InternalCoroutinesApi
        fun getInstance(context: Context): SubscriberDatabase {
            synchronized(this) {// this will ensure that only one application can access this

                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(

                        context.applicationContext,
                        SubscriberDatabase::class.java,
                        "subscriber_data_database"
                    ).build()
                }

                return instance

            }
        }
    }
}