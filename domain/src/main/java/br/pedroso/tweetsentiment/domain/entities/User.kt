package br.pedroso.tweetsentiment.domain

/**
 * Created by felipe on 08/03/18.
 */
data class User(
        val id: Long,
        val userName: String,
        val name: String,
        val bannerUrl: String,
        val profilePictureUrl: String)