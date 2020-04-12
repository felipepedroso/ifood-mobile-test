package br.pedroso.tweetsentiment.network.twitter.retrofit.mappers

import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.network.twitter.retrofit.entities.RetrofitTweet
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat


class TweetMapper {
    companion object {
        private const val TWITTER_DATE_FORMAT = "EEE MMM dd HH:mm:ss Z yyyy"

        fun mapTwitterApiToDomain(retrofitTweet: RetrofitTweet): Tweet {
            with(retrofitTweet) {
                val creationTimestamp =
                    DateTime.parse(createdAt, DateTimeFormat.forPattern(TWITTER_DATE_FORMAT))
                return Tweet(
                    id = id,
                    text = text,
                    creationTimestamp = creationTimestamp
                )
            }
        }
    }
}
