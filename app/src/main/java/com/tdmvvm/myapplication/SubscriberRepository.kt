package com.tdmvvm.myapplication

import com.tdmvvm.myapplication.db.Subscriber
import com.tdmvvm.myapplication.db.SubscriberDao

class SubscriberRepository( private val dao: SubscriberDao) {

    val subscribers = dao.getAllSubscriber()


    suspend fun insert(subscriber: Subscriber):Long{
        return dao.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber):Int{
        return dao.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber):Int{
        return dao.deleteSubscriber(subscriber)
    }


    suspend fun deleteall():Int{
        return dao.deleteAll()
    }
}