package br.pedroso.tweetsentiment.device.storage.database.room.mappers

import br.pedroso.tweetsentiment.device.storage.database.room.entities.RoomUser
import br.pedroso.tweetsentiment.domain.entities.User

class RoomUserMapper {

    companion object {
        fun mapRoomToDomain(roomUser: RoomUser): User {
            with(roomUser) {
                return User(
                    id = id,
                    userName = userName,
                    name = name,
                    bannerUrl = bannerUrl,
                    profilePictureUrl = profilePictureUrl
                )
            }
        }

        fun mapDomainToRoom(user: User): RoomUser {
            with(user) {
                return RoomUser(
                    id = id,
                    userName = userName,
                    name = name,
                    bannerUrl = bannerUrl,
                    profilePictureUrl = profilePictureUrl
                )
            }
        }
    }
}
