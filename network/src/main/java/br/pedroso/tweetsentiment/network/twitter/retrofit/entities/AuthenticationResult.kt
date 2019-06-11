package br.pedroso.tweetsentiment.network.twitter.retrofit.entities

import com.google.gson.annotations.SerializedName

data class AuthenticationResult(
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("access_token") val accessToken: String
)
