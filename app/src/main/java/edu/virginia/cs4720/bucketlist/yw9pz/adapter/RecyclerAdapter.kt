package edu.virginia.cs4720.bucketlist.yw9pz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.virginia.cs4720.bucketlist.yw9pz.R
import edu.virginia.cs4720.bucketlist.yw9pz.model.BucketList

class RecyclerAdapter (private val context: Context, private val dataset: List<BucketList>)
    : RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>(){

    var onItemClick: ((BucketList) -> Unit)? = null

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val anItem: CheckBox = view.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_items, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        if (item.status == 0) {
            holder.anItem.text = item.task + "\nDue: " + item.getDueDate()
        }
        else {
            holder.anItem.text = item.task + "\nFinished: " + item.getFinishDate()
        }
        holder.anItem.isChecked = toBoolean(item.status)
        holder.view.setOnClickListener() {
            onItemClick?.invoke(item)
        }
    }

    private fun toBoolean(n: Int): Boolean {
        return n!=0
    }


    override fun getItemCount() = dataset.size
}