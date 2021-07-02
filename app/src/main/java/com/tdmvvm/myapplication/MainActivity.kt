package com.tdmvvm.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tdmvvm.myapplication.databinding.ActivityMainBinding
import com.tdmvvm.myapplication.db.Subscriber
import com.tdmvvm.myapplication.db.SubscriberDatabase
import com.tdmvvm.myapplication.db.SubscriverVIewModel
import kotlinx.coroutines.InternalCoroutinesApi
import java.lang.reflect.Array.get

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var subscriverVIewModel: SubscriverVIewModel
    private lateinit var adapter: MyRecyclerViewAdapter


    // Course Video  https://www.youtube.com/watch?v=v2yocpEcE_g
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        val dao = SubscriberDatabase.getInstance(application).subscriberDao
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)

        subscriverVIewModel = ViewModelProvider(this,factory).get(SubscriverVIewModel::class.java)

        binding.myViewModel=subscriverVIewModel
        binding.lifecycleOwner = this
        initRecylerView()
        subscriverVIewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })


    }

    private fun initRecylerView(){
        binding.recylerview.layoutManager=LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter({selecteditem: Subscriber ->listItemClicked(selecteditem)})
        binding.recylerview.adapter = adapter
        displaySubscriberList()
    }

    private fun displaySubscriberList(){
        subscriverVIewModel.subscribers.observe(this, Observer {

//            for below code
            //    @Query("SELECT * FROM Subscriber_date_Table")
            //    fun getAllSubscriber():LiveData<List<Subscriber>>

            adapter.setList(it)

            Log.wtf("KISLAM",it.toString())

            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(subscriber: Subscriber){
//        Toast.makeText(applicationContext, "selected name si ${subscriber.name}\"", Toast.LENGTH_SHORT).show()
        subscriverVIewModel.initUpdateDelete(subscriber)

    }
}