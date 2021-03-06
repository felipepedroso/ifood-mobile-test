package br.pedroso.tweetsentiment.domain.entities

data class User(
    val id: Long,
    val userName: String,
    val name: String,
    val bannerUrl: String,
    val profilePictureUrl: String
)
