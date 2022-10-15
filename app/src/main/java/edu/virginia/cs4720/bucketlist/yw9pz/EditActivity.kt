package edu.virginia.cs4720.bucketlist.yw9pz

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import edu.virginia.cs4720.bucketlist.yw9pz.model.BucketList
import java.text.SimpleDateFormat
import java.util.*


class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Edit"

        val editTask = findViewById<EditText>(R.id.editItem) //task
        val dueDateShow = findViewById<TextView>(R.id.showsDueDate) //Due date:
        val dueDateButton = findViewById<Button>(R.id.textViewForDueDate) //pick a due date
        val checkBox: CheckBox = findViewById<CheckBox>(R.id.checkBoxOnEdit) //done?
        val finishDateTextView: TextView = findViewById<TextView>(R.id.showsFinishDate) //Finish date:
        val pickFinishDateButton: Button = findViewById<Button>(R.id.buttonForFinishDate) //pick a finish date
        val saveButton = findViewById<Button>(R.id.editedTaskButton) //save

        //calendar for today
        val todayCalendar = Calendar.getInstance(TimeZone.getTimeZone("US/Eastern"))

        //if due date is changed
        var newDueDate: Long = Long.MIN_VALUE
        var dueDateIsChanged: Boolean = false
        var oldDueDate: Long = Long.MIN_VALUE

        //if finish date is changed
        var newFinishDate: Long = Long.MIN_VALUE
        var finishDateIsChanged: Boolean = false
        var oldFinishDate: Long = 0

        val index = intent.getIntExtra(ListActivity.INDEX_DATA, 0) //which element in the list to change
        intent.getParcelableExtra<BucketList>("aBucketList")
            ?.let {
                val status = intent.getIntExtra("status", 0)
                val finishDate = intent.getLongExtra("finishDate", 0)
                val strDueDate = it.getDueDate()
                val year = strDueDate.substring(strDueDate.length-4).toInt()
                val monthStr = strDueDate.substring(0, 3)
                val month = convertToNumber(monthStr)
                val day = strDueDate.substring(4, 6).trim().toInt()
                editTask.setText(it.task)
                checkBox.isChecked = toBoolean(status)
                //Log.i("real status", it.statusToBeSet.toString())
                oldDueDate = it.dueDate
                oldFinishDate = finishDate
                dueDateShow.text = "Due date: " + dateToString(it.dueDate)
                if (!checkBox.isChecked) {
                    finishDateTextView.visibility = View.INVISIBLE
                    pickFinishDateButton.visibility = View.INVISIBLE
                }
                else { //if done (being done = def has a finish date)
                    finishDateTextView.text = "Finish date: " + dateToString(finishDate)
                }

                dueDateButton.setOnClickListener() {
                    val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view:DatePicker,
                    mYear:Int, mMonth:Int, mDay:Int ->
                        //set to TextView
                        dueDateShow.text = "Due Date: " + convertToString(mMonth) + " " + mDay.toString() + " " + mYear.toString()
                        val calendar = Calendar.getInstance(TimeZone.getTimeZone("US/Eastern"))
                        calendar.set(mYear, mMonth, mDay)
                        newDueDate = calendar.timeInMillis
                        dueDateIsChanged = true
                    },year, month, day)
                    dpd.show()
                }

                //checkbox
                checkBox.setOnClickListener() {
                    if (checkBox.isChecked) {
                        finishDateTextView.visibility = View.VISIBLE
                        if (finishDateIsChanged) {
                            finishDateTextView.text = "Finish Date: " + dateToString(newFinishDate)
                        }
                        else {
                            if (oldFinishDate == 0L) {
                                finishDateTextView.text = "Finish Date: " + dateToString(todayCalendar.timeInMillis)
                            }
                            else {
                                finishDateTextView.text = "Finish Date: " + dateToString(oldFinishDate)
                            }
                        }
                        pickFinishDateButton.visibility = View.VISIBLE
                    }
                    else {
                        finishDateTextView.visibility = View.INVISIBLE
                        pickFinishDateButton.visibility = View.INVISIBLE
                    }
                }

                //pick finish date
                val c = Calendar.getInstance()
                val yearF = c.get(Calendar.YEAR)
                val monthF = c.get(Calendar.MONTH)
                val dayF = c.get(Calendar.DAY_OF_MONTH)
                pickFinishDateButton.setOnClickListener() {
                    val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view:DatePicker,
                                                mYear:Int, mMonth:Int, mDay:Int ->
                        //set to TextView
                        finishDateTextView.text = "Finish Date: " + convertToString(mMonth) + " " + mDay.toString() + " " + mYear.toString()
                        val calendar = Calendar.getInstance(TimeZone.getTimeZone("US/Eastern"))
                        calendar.set(mYear, mMonth, mDay)
                        newFinishDate = calendar.timeInMillis
                        finishDateIsChanged = true
                    },yearF, monthF, dayF)
                    dpd.datePicker.maxDate = System.currentTimeMillis()
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
            val status = booleanToInt(checkBox.isChecked)
            val finishDate: Long = if(finishDateIsChanged) {
                newFinishDate
            }
            else {
                oldFinishDate
            }
            val task: String = findViewById<EditText>(R.id.editItem).text.toString()
            val newItem = BucketList(2, status, task, dueDate, finishDate)
            val intent = Intent(this, ListActivity::class.java)
            intent.putExtra(ListActivity.EDIT_ITEM, newItem)
            intent.putExtra(ListActivity.INDEX_DATA, index)
            startActivity(intent)
        }
    }

    private fun convertToNumber(month: String): Int {
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

    private fun convertToString(month: Int): String {
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

    private fun booleanToInt(b: Boolean): Int {
        return if (b) {
            1
        } else {
            0
        }
    }

    private fun dateToString(date: Long): String {
        val simpleDateFormat = SimpleDateFormat()
        simpleDateFormat.timeZone = TimeZone.getTimeZone("US/Eastern")
        return SimpleDateFormat("MMM d yyyy").format(Date(date))
    }
}