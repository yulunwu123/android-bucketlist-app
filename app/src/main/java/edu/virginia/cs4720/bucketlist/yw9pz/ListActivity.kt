package edu.virginia.cs4720.bucketlist.yw9pz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.virginia.cs4720.bucketlist.yw9pz.adapter.RecyclerAdapter
import java.util.*
import edu.virginia.cs4720.bucketlist.yw9pz.model.BucketList
import java.sql.Timestamp

class ListActivity : AppCompatActivity() {
    private var indexOfToBeEditedItem: Int = Int.MAX_VALUE

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
        const val INDEX_DATA = "indexOfEditedItem"
        const val EDIT_ITEM = "EDIT_ITEM"
        private val timestamp = Timestamp(System.currentTimeMillis())

        var myDataset :MutableList<BucketList> = mutableListOf(
            BucketList(1, 0, "Attend Rotunda Sing", timestamp.time, 0),
            BucketList(1, 0, "Walk the Lawn", 1686024000000, 0),
            BucketList(1, 1, "Study in the Dome Room of the Rotunda", 1686024000000, 1665028800000),
            BucketList(1, 1, "Pull an All-Nighter at Clemons", 1686024000000, 1665720000000))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.TitleText).visibility = View.VISIBLE

        if (intent.hasExtra(EXTRA_DATA)) {
            val newItem = intent.getParcelableExtra<BucketList>(EXTRA_DATA)
            if (newItem != null) {
                myDataset.add(newItem)
            }
        }

        val index = intent.getIntExtra(INDEX_DATA, 0)
        if (intent.hasExtra(EDIT_ITEM)) {
            intent.getParcelableExtra<BucketList>(EDIT_ITEM)
                ?.let {
                    myDataset[index] = it
                }
        }

        myDataset.sortWith(compareBy({it.getRealStatus()}, {it.getRealFinishDate()}, {it.dueDate}))
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val bucketListAdapter = RecyclerAdapter(this, myDataset)
        recyclerView.adapter = bucketListAdapter
        recyclerView.setHasFixedSize(true)

        //edit activity
        bucketListAdapter.onItemClick = {
            indexOfToBeEditedItem = myDataset.indexOf(it)
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("status", myDataset[indexOfToBeEditedItem].getRealStatus())
            intent.putExtra("task", myDataset[indexOfToBeEditedItem].task)
            intent.putExtra("dueDate", myDataset[indexOfToBeEditedItem].dueDate)
            intent.putExtra("finishDate", myDataset[indexOfToBeEditedItem].getRealFinishDate())
            intent.putExtra(INDEX_DATA, indexOfToBeEditedItem)
            intent.putExtra("aBucketList", myDataset[indexOfToBeEditedItem])
            startActivity(intent)
        }

        //add activity
        findViewById<FloatingActionButton>(R.id.floatingAddButton).setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }
    }

    fun mySort(l: MutableList<BucketList>) {
        l.sortWith(compareBy({it.getRealStatus()}, {it.getRealFinishDate()}, {it.dueDate}))
    }
}