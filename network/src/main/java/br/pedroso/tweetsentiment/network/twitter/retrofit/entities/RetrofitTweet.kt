package br.pedroso.tweetsentiment.network.twitter.retrofit.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by felip on 10/03/2018.
 */
data class RetrofitTweet(
        val id: Long,
        val text: String,
        @SerializedName("created_at") val createdAt: String)