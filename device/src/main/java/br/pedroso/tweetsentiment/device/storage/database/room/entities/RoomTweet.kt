package br.pedroso.tweetsentiment.device.storage.database.room.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

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