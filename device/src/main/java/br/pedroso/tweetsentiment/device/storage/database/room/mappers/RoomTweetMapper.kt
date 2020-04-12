package br.pedroso.tweetsentiment.device.storage.database.room.mappers

import br.pedroso.tweetsentiment.device.storage.database.room.entities.RoomTweet
import br.pedroso.tweetsentiment.domain.entities.Sentiment
import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.entities.User
import org.joda.time.DateTime

class RoomTweetMapper {

    companion object {
        fun mapDomainToRoom(user: User, tweet: Tweet): RoomTweet {
            with(tweet) {
                return RoomTweet(
                    id = id,
                    text = text,
                    createdAtTimestamp = creationTimestamp.millis,
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
                    creationTimestamp = DateTime(createdAtTimestamp),
                    sentiment = Sentiment.valueOf(sentiment)
                )
            }
        }
    }
}
