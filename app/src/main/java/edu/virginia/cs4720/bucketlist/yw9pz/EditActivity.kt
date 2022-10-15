package edu.virginia.cs4720.bucketlist.yw9pz

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import edu.virginia.cs4720.bucketlist.yw9pz.model.BucketList
import java.util.*


class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Edit"

        val edit_view = findViewById<EditText>(R.id.editItem)
        val due_date = findViewById<TextView>(R.id.showsDueDate)
        val dueDateButton = findViewById<Button>(R.id.textViewForDueDate)

        //if due date is changed
        var newDueDate: Long = Long.MIN_VALUE
        var dueDateIsChanged: Boolean = false
        var oldDueDate: Long = Long.MIN_VALUE


        val saveButton = findViewById<Button>(R.id.editedTaskButton)
        val index = intent.getIntExtra(ListActivity.INDEX_DATA, 0)
        val checkBox: CheckBox = findViewById<CheckBox>(R.id.checkBoxOnEdit)

        intent.getParcelableExtra<BucketList>("aBucketList")
            ?.let {
                val strDueDate = it.getDueDate()
                val year = strDueDate.substring(strDueDate.length-4).toInt()
                val monthStr = strDueDate.substring(0, 3)
                val month = convertToNumber(monthStr)
                val day = strDueDate.substring(4, 6).trim().toInt()

                edit_view.setText(it.task)
                checkBox.isChecked = toBoolean(it.status)
                due_date.text = "Due Date: " + it.getDueDate()
                oldDueDate = it.dueDate

                dueDateButton.setOnClickListener() {
                    val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view:DatePicker,
                    mYear:Int, mMonth:Int, mDay:Int ->
                        //set to TextView
                        due_date.text = "Due Date: " + convertToString(mMonth) + " " + mDay.toString() + " " + mYear.toString()
                        val calendar = Calendar.getInstance(TimeZone.getTimeZone("US/Eastern"))
                        calendar.set(mYear, mMonth, mDay)
                        newDueDate = calendar.timeInMillis
                        dueDateIsChanged = true
                    },year, month, day)
                    dpd.show()
                }
            }





        // perform click event on save button
        saveButton.setOnClickListener() {
            val dueDate: Long = if (dueDateIsChanged) {
                newDueDate
            } else {
                oldDueDate
            }
            val status = intToBoolean(checkBox.isChecked)

            val task: String = findViewById<EditText>(R.id.editItem).text.toString()
            val newItem = BucketList(2, status, task, dueDate)
            val intent = Intent(this, ListActivity::class.java)
            intent.putExtra(ListActivity.EDIT_ITEM, newItem)
            intent.putExtra(ListActivity.INDEX_DATA, index)

            startActivity(intent)
        }

    }


    fun convertToNumber(month: String): Int {
        val result = when (month) {
            "Jan" -> 0
            "Feb" -> 1
            "Mar" -> 2
            "Apr" -> 3
            "May" -> 4
            "Jun" -> 5
            "Jul" -> 6
            "Aug" -> 7
            "Sep" -> 8
            "Oct" -> 9
            "Nov" -> 10
            else -> 11
        }
        return result
    }

    fun convertToString(month: Int): String {
        val result = when (month) {
            0 -> "Jan"
            1 -> "Feb"
            2 -> "Mar"
            3 -> "Apr"
            4 -> "May"
            5 -> "Jun"
            6 -> "Jul"
            7 -> "Aug"
            8 -> "Sep"
            9 -> "Oct"
            10 -> "Nov"
            else -> "Dec"
        }
        return result
    }

    private fun toBoolean(n: Int): Boolean {
        return n!=0
    }

    private fun intToBoolean(b: Boolean): Int {
        return if (b) {
            1
        } else {
            0
        }
    }
}