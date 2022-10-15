package edu.virginia.cs4720.bucketlist.yw9pz.data
import edu.virginia.cs4720.bucketlist.yw9pz.model.BucketList
import java.sql.Timestamp

val timestamp = Timestamp(System.currentTimeMillis())
val list: MutableList<BucketList> = mutableListOf(
    BucketList(1, 0, "Attend Rotunda Sing", timestamp.time),
    BucketList(1, 0, "Streak the Lawn", timestamp.time))

class Datasource {
    val timestamp = Timestamp(System.currentTimeMillis())
    val in_class_list: MutableList<BucketList> = mutableListOf(
        BucketList(1, 0, "Attend Rotunda Sing", timestamp.time),
        BucketList(1, 0, "Streak the Lawn", timestamp.time))

    fun loadItems(): MutableList<BucketList> {
        return list
    }

    fun addItem(item: BucketList) {
        list.add(item)
    }


}