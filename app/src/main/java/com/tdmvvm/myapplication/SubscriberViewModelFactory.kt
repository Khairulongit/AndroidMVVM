package com.tdmvvm.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tdmvvm.myapplication.db.SubscriverVIewModel
import java.lang.*

class SubscriberViewModelFactory(private val repository: SubscriberRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SubscriverVIewModel::class.java)){
            return SubscriverVIewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}