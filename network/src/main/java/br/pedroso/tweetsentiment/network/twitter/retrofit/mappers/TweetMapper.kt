package br.pedroso.tweetsentiment.network.twitter.retrofit.mappers

import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.network.twitter.retrofit.entities.RetrofitTweet
import java.text.SimpleDateFormat
import java.util.Locale

class TweetMapper {
    companion object {
        private const val TWITTER_DATE_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"

        fun mapTwitterApiToDomain(retrofitTweet: RetrofitTweet): Tweet {
            with(retrofitTweet) {
                val creationTimestamp =
                    SimpleDateFormat(TWITTER_DATE_FORMAT, Locale.ENGLISH).parse(createdAt)

                return Tweet(
                    id = id,
                    text = text,
                    creationTimestamp = creationTimestamp
                )
            }
        }
    }
}
