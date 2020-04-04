package br.pedroso.tweetsentiment.device.storage.database.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tweets",
    foreignKeys = [
        ForeignKey(
            entity = RoomUser::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("user_id")
        )
    ]
)
data class RoomTweet(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "created_at") val createdAtTimestamp: Long,
    @ColumnInfo(name = "sentiment") val sentiment: String,
    @ColumnInfo(name = "user_id") val userId: Long
)
