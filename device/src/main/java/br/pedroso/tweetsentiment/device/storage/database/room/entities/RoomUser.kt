package br.pedroso.tweetsentiment.device.storage.database.room.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by felipe on 09/03/2018.
 */
@Entity(tableName = "users")
data class RoomUser(
        @PrimaryKey val id: Long,
        val userName: String,
        val name: String,
        val bannerUrl: String,
        val profilePictureUrl: String)