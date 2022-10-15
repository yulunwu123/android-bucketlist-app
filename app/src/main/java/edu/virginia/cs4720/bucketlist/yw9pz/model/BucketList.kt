package edu.virginia.cs4720.bucketlist.yw9pz.model

import android.os.Parcel
import java.text.SimpleDateFormat
import java.util.*
import android.os.Parcelable

data class BucketList(
    val stringResourceId: Int, val status: Int,
    val task: String, val dueDate: Long, val finishDate: Long) :Parcelable  {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readLong(),
    )

    var statusToBeSet = status
    var finishDateToBeSet = finishDate

    fun setRealStatus(s: Int) {
        statusToBeSet = s
    }

    fun setRealFinishDate(d: Long) {
        finishDateToBeSet = d
    }

    fun getRealStatus(): Int {
        return statusToBeSet
    }

    fun getRealFinishDate(): Long {
        return if (statusToBeSet == 1) {
            finishDateToBeSet
        } else {
            0
        }
    }

    fun getDueDate(): String {
        val simpleDateFormat = SimpleDateFormat()
        simpleDateFormat.timeZone = TimeZone.getTimeZone("US/Eastern")
        return SimpleDateFormat("MMM d yyyy").format(Date(dueDate))
    }

    fun getFinishDate(): String {
        val simpleDateFormat = SimpleDateFormat()
        simpleDateFormat.timeZone = TimeZone.getTimeZone("US/Eastern")
        return SimpleDateFormat("MMM d, yyyy").format(Date(finishDateToBeSet))
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(stringResourceId)
        parcel.writeInt(status)
        parcel.writeString(task)
        parcel.writeLong(dueDate)
        parcel.writeLong(finishDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BucketList> {
        override fun createFromParcel(parcel: Parcel): BucketList {
            return BucketList(parcel)
        }

        override fun newArray(size: Int): Array<BucketList?> {
            return arrayOfNulls(size)
        }
    }
}