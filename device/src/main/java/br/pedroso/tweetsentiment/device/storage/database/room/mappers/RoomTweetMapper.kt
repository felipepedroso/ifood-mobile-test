package br.pedroso.tweetsentiment.device.storage.database.room.mappers

import br.pedroso.tweetsentiment.device.storage.database.room.entities.RoomTweet
import br.pedroso.tweetsentiment.domain.Sentiment
import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.domain.User
import java.util.*

class RoomTweetMapper {

    companion object {
        fun mapDomainToRoom(user: User, tweet: Tweet): RoomTweet {
            with(tweet) {
                return RoomTweet(
                        id = id,
                        text = text,
                        createdAtTimestamp = creationTimestamp.time,
                        sentiment = sentiment.name,
                        userId = user.id
                )
            }
        }

        fun mapRoomToDomain(roomTweet: RoomTweet): Tweet {
            with(roomTweet) {
                return Tweet(
                        id = id,
                        text = text,
                        creationTimestamp = Date(createdAtTimestamp),
                        sentiment = Sentiment.valueOf(sentiment)
                )
            }
        }
    }
}