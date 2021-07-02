package com.tdmvvm.myapplication.db

import android.util.MutableDouble
import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tdmvvm.myapplication.Event
import com.tdmvvm.myapplication.SubscriberRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscriverVIewModel(private val repository: SubscriberRepository) : ViewModel(), Observable {


    val subscribers = repository.subscribers

    private var isUpdatedorDelete = false
    private lateinit var subscribertoUpdateOrDelete: Subscriber

    @Bindable
    val inputname = MutableLiveData<String>()

    @Bindable
    val inputemail = MutableLiveData<String>()

    @Bindable
    val saveorUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllorDeleteButtonText = MutableLiveData<String>()

    private val statusmessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusmessage


    init {
        saveorUpdateButtonText.value = "Save"
        clearAllorDeleteButtonText.value = "Clear All"
    }


    fun saveOrupdate() {



        if (isUpdatedorDelete) {
            subscribertoUpdateOrDelete.name = inputname.value!!
            subscribertoUpdateOrDelete.email = inputemail.value!!

            update(subscribertoUpdateOrDelete)

        } else {

            val name = inputname.value!!
            val email = inputemail.value!!

            insert(Subscriber(0, name, email))

            inputname.value = null
            inputemail.value = null
        }


    }


    fun clearAllorDelete() {

        if (isUpdatedorDelete) {
            delete(subscribertoUpdateOrDelete)
        } else
            clearall()


    }


    fun insert(subscriber: Subscriber): Job =// viewmodelscope will run in the background thread
        viewModelScope.launch {
            val newRowId=repository.insert(subscriber)
            if (newRowId>-1){
            statusmessage.value= Event("Subscriber Inserted Successfully $newRowId")}
            else{
                statusmessage.value= Event("Error Occured")
            }
        }


    fun update(subscriber: Subscriber): Job =// viewmodelscope will run in the background thread
        viewModelScope.launch {

            val noOfRows=repository.update(subscriber)
            if (noOfRows>0) {
                inputemail.value = null
                inputname.value = null
                isUpdatedorDelete = false
                saveorUpdateButtonText.value = "Save"
                clearAllorDeleteButtonText.value = "Clear All"
                statusmessage.value = Event("$noOfRows Updated Successfully")
            }
            else{
                statusmessage.value = Event("Error Occurred at Update")

            }


        }


    fun delete(subscriber: Subscriber): Job =// viewmodelscope will run in the background thread
        viewModelScope.launch {


            val noofRowsdeleted=repository.delete(subscriber)
            if (noofRowsdeleted>0) {

                inputemail.value = null
                inputname.value = null
                isUpdatedorDelete = false
                saveorUpdateButtonText.value = "Save"
                clearAllorDeleteButtonText.value = "Clear All"
                statusmessage.value = Event("$noofRowsdeleted Deleted Successfully")
            }
            else
                statusmessage.value = Event("Error Occurred at Deletion")



        }




    fun clearall(): Job = viewModelScope.launch {
        val noofRowsDeleted=repository.deleteall()

        if (noofRowsDeleted>0) {
            statusmessage.value = Event("$noofRowsDeleted Subscribers Deleted Successfully")
        }else{
            statusmessage.value = Event("Error Occurred while Deleting All")

        }


    }

    fun initUpdateDelete(subscriber: Subscriber) {
        inputemail.value = subscriber.email
        inputname.value = subscriber.name
        isUpdatedorDelete = true
        subscribertoUpdateOrDelete = subscriber
        saveorUpdateButtonText.value = "Update"
        clearAllorDeleteButtonText.value = "Delete"


    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }


}
