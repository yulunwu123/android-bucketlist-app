package edu.virginia.cs4720.bucketlist.yw9pz.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import edu.virginia.cs4720.bucketlist.yw9pz.ListActivity
import edu.virginia.cs4720.bucketlist.yw9pz.R
import edu.virginia.cs4720.bucketlist.yw9pz.model.BucketList
import java.util.*

class RecyclerAdapter ( val context: Context,  val dataset: MutableList<BucketList>)
    : RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>(){

    var onItemClick: ((BucketList) -> Unit)? = null
    var onCheckboxClick: ((BucketList) -> Unit)? = null

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
        if (item.getRealStatus() == 0) {
            holder.anItem.text = item.task + "\nDue: " + item.getDueDate()
        }
        else {
            holder.anItem.text = item.task + "\nFinished: " + item.getFinishDate()
        }
        holder.anItem.isChecked = toBoolean(item.getRealStatus())
        holder.view.setOnClickListener() {
            onItemClick?.invoke(item)
        }

        holder.anItem.setOnClickListener {
            onCheckboxClick?.invoke(item)
            item.setRealStatus(booleanToInt(holder.anItem.isChecked))
            val todayCalendar = Calendar.getInstance(TimeZone.getTimeZone("US/Eastern"))
            if (holder.anItem.isChecked) {
                item.setRealFinishDate(todayCalendar.timeInMillis)
            }
            dataset.sortWith(compareBy({it.getRealStatus()}, {it.getRealFinishDate()}, {it.dueDate}))
            notifyDataSetChanged()
            //val context = holder.itemView.context as ListActivity
            //context.mySort(dataset)
        }
    }

    private fun toBoolean(n: Int): Boolean {
        return n!=0
    }

    private fun booleanToInt(b: Boolean): Int {
        return if (b) {
            1
        } else {
            0
        }
    }

    override fun getItemCount() = dataset.size


}