package br.pedroso.tweetsentiment.network.twitter.retrofit.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by felip on 10/03/2018.
 */
class RetrofitTweet(
        val id: Long,
        @SerializedName(value = "text", alternate = ["full_text"]) val text: String,
        @SerializedName("created_at") val createdAt: String)