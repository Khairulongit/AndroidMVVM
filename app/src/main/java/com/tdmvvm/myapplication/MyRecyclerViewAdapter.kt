package com.tdmvvm.myapplication

import android.database.DatabaseUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tdmvvm.myapplication.databinding.ListItemBinding
import com.tdmvvm.myapplication.db.Subscriber

class MyRecyclerViewAdapter(private val clicklister:(Subscriber)->Unit) :
    RecyclerView.Adapter<MyViewHolder>() {

    private val subscriberslist=ArrayList<Subscriber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater:LayoutInflater = LayoutInflater.from(parent.context)
        val binding :ListItemBinding= DataBindingUtil.inflate(layoutInflater,R.layout.list_item,parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(subscriberslist[position],clicklister)
    }

    fun setList(subscribers: List<Subscriber>) {
        subscriberslist.clear()
        subscriberslist.addAll(subscribers)
    }

    override fun getItemCount(): Int {

        return subscriberslist.size
    }

}


// ListItemBinding for the layout item_list
class MyViewHolder(val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(subscriber: Subscriber,clicklister:(Subscriber)->Unit){
        binding.nametexyview.text=subscriber.name
        binding.emailtextview.text=subscriber.email
        binding.listItemLayout.setOnClickListener{
            clicklister(subscriber)
        }
    }
}