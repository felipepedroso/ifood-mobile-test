package br.pedroso.tweetsentiment.network.twitter.retrofit.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by felip on 10/03/2018.
 */
data class RetrofitUser(
        val id: Long,
        @SerializedName("screen_name") val userName: String,
        val name: String,
        @SerializedName("profile_banner_url") val bannerUrl: String,
        @SerializedName("profile_image_url") val profilePictureUrl: String)