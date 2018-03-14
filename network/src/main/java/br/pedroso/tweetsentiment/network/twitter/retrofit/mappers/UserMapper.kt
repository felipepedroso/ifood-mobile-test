package br.pedroso.tweetsentiment.network.twitter.retrofit.mappers

import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.network.twitter.retrofit.entities.RetrofitUser

/**
 * Created by felip on 10/03/2018.
 */
class UserMapper {
    companion object {
        fun mapRetrofitToDomain(retrofitUser: RetrofitUser): User {
            with(retrofitUser) {
                return User(
                        id = id,
                        name = name,
                        userName = userName,
                        bannerUrl = bannerUrl ?: "http://lorempixel.com/1500/500/abstract",
                        profilePictureUrl = profilePictureUrl?.replace("_normal", "") ?: ""
                )
            }
        }
    }
}