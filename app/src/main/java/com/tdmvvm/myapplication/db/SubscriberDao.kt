    package com.tdmvvm.myapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscriber(subscriber: Subscriber):Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber):Int

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber):Int

    @Query("DELETE FROM Subscriber_date_Table")
    suspend fun deleteAll():Int



    // we didnt include the suspend modifier as the return is Live data so room will perform this
    // task in back ground
    @Query("SELECT * FROM Subscriber_date_Table")
    fun getAllSubscriber():LiveData<List<Subscriber>>


}