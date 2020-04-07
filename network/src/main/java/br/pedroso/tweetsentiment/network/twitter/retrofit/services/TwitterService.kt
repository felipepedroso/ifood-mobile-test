package br.pedroso.tweetsentiment.network.twitter.retrofit.services

import br.pedroso.tweetsentiment.network.twitter.retrofit.constants.TWITTER_API_VERSION
import br.pedroso.tweetsentiment.network.twitter.retrofit.entities.RetrofitTweet
import br.pedroso.tweetsentiment.network.twitter.retrofit.entities.RetrofitUser
import retrofit2.http.GET
import retrofit2.http.Query

interface TwitterService {
    @GET("/$TWITTER_API_VERSION/users/show.json")
    suspend fun usersShow(@Query("screen_name") screenName: String): RetrofitUser

    @GET("/$TWITTER_API_VERSION/statuses/user_timeline.json")
    suspend fun statusesUserTimeline(
        @Query("screen_name") screenName: String,
        @Query("since_id") sinceId: Long? = null
    ): List<RetrofitTweet>
}
