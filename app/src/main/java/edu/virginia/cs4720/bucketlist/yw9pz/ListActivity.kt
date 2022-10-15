package edu.virginia.cs4720.bucketlist.yw9pz

import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_DATA
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.virginia.cs4720.bucketlist.yw9pz.adapter.RecyclerAdapter
import java.util.*
import edu.virginia.cs4720.bucketlist.yw9pz.model.BucketList
import edu.virginia.cs4720.bucketlist.yw9pz.data.Datasource
import java.sql.Timestamp

//If this page does not show up automatically after opening the app, change the file name back to "MainActivity.kt"
class ListActivity : AppCompatActivity() {

    var indexOfToBeEditedItem: Int = Int.MAX_VALUE

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
        const val INDEX_DATA = "indexOfEditedItem"
        const val EDIT_ITEM = "EDIT_ITEM"
        private val timestamp = Timestamp(System.currentTimeMillis())

        var myDataset :MutableList<BucketList> = mutableListOf(
            BucketList(1, 0, "Attend Rotunda Sing", timestamp.time),
            BucketList(1, 0, "Streak the Lawn", 1686024000000),
            BucketList(1, 1, "Study in the Dome Room of the Rotunda", 1686024000000, 1665028800000),
            BucketList(1, 1, "Pull an All-Nighter at Clemons", 1686024000000, 1665720000000))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val timestamp = Timestamp(System.currentTimeMillis())
//        var myDataset :MutableList<BucketList> = mutableListOf(
//            BucketList(1, 0, "Attend Rotunda Sing", timestamp.time),
//            BucketList(1, 0, "Streak the Lawn", timestamp.time))
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
                    myDataset.set(index, it)
                }
        }

        myDataset.sortWith(compareBy({it.status}, {it.finishDate}, {it.dueDate}))
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        //recyclerView.layoutManager = LinearLayoutManager(this)
        val bucketListAdapter = RecyclerAdapter(this, myDataset)
        recyclerView.adapter = bucketListAdapter
        recyclerView.setHasFixedSize(true)

        bucketListAdapter.onItemClick = {
            indexOfToBeEditedItem = myDataset.indexOf(it)
            Log.i("index is:", indexOfToBeEditedItem.toString())
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("aBucketList", it)
            intent.putExtra("indexOfEditedItem", indexOfToBeEditedItem)
            startActivity(intent)
        }

        findViewById<FloatingActionButton>(R.id.floatingAddButton).setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }

    }
}