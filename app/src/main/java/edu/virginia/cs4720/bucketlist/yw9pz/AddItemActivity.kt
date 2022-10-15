package edu.virginia.cs4720.bucketlist.yw9pz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import edu.virginia.cs4720.bucketlist.yw9pz.model.BucketList
import java.util.*
import edu.virginia.cs4720.bucketlist.yw9pz.data.Datasource


class AddItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_item)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Add"

        val simpleDatePicker = findViewById<DatePicker>(R.id.datePicker)
        val saveButton = findViewById<Button>(R.id.newTaskButton)

        // perform click event on save button
        saveButton.setOnClickListener() {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("US/Eastern"))
            calendar.set(simpleDatePicker.year, simpleDatePicker.month, simpleDatePicker.dayOfMonth)
            val due_date_in_milliseconds = calendar.timeInMillis
            val task: String = findViewById<EditText>(R.id.newTaskText).text.toString()
            val newItem = BucketList(2, 0, task, due_date_in_milliseconds)
            //Datasource().addItem(newItem)
            val intent = Intent(this, ListActivity::class.java)
            intent.putExtra(ListActivity.EXTRA_DATA, newItem)
            startActivity(intent)
        }
    }
}