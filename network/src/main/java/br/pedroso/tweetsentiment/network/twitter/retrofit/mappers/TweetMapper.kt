package br.pedroso.tweetsentiment.network.twitter.retrofit.mappers

import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.network.twitter.retrofit.entities.RetrofitTweet
import khronos.toDate
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by felip on 10/03/2018.
 */
class TweetMapper {
    companion object {
        // TODO: fix locale problem
        private const val TWITTER_DATE_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"

        fun mapTwitterApiToDomain(retrofitTweet: RetrofitTweet): Tweet {
            with(retrofitTweet) {
                var creationTimestamp = SimpleDateFormat(TWITTER_DATE_FORMAT, Locale.ENGLISH).parse(createdAt)

                return Tweet(
                        id = id,
                        text = text,
                        creationTimestamp = creationTimestamp
                )
            }
        }
    }
}