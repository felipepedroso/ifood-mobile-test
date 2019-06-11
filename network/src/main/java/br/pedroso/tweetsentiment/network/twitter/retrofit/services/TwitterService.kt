package br.pedroso.tweetsentiment.network.twitter.retrofit.services

import br.pedroso.tweetsentiment.network.twitter.retrofit.constants.TWITTER_API_VERSION
import br.pedroso.tweetsentiment.network.twitter.retrofit.entities.RetrofitTweet
import br.pedroso.tweetsentiment.network.twitter.retrofit.entities.RetrofitUser
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface TwitterService {
    @GET("/$TWITTER_API_VERSION/users/show.json")
    fun usersShow(@Query("screen_name") screenName: String): Observable<RetrofitUser>

    @GET("/$TWITTER_API_VERSION/statuses/user_timeline.json")
    fun statusesUserTimeline(
        @Query("screen_name") screenName: String,
        @Query("since_id") sinceId: Long? = null
    ): Observable<List<RetrofitTweet>>
}
