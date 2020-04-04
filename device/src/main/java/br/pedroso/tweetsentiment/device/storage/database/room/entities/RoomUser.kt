package br.pedroso.tweetsentiment.device.storage.database.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class RoomUser(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "username") val userName: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "banner_url") val bannerUrl: String,
    @ColumnInfo(name = "profile_picture_url") val profilePictureUrl: String
)
