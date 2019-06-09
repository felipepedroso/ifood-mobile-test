package br.pedroso.tweetsentiment.device.storage.database.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "tweets",
        foreignKeys = [
            ForeignKey(
                    entity = RoomUser::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("userId"))
        ])
data class RoomTweet(
        @PrimaryKey val id: Long,
        val text: String,
        val createdAtTimestamp: Long,
        val sentiment: String,
        val userId: Long)