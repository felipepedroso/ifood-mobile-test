package br.pedroso.tweetsentiment.network.twitter.retrofit.services

import br.pedroso.tweetsentiment.network.twitter.retrofit.entities.AuthenticationResult
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TwitterAuthService {
    @FormUrlEncoded
    @POST("oauth2/token")
    fun applicationOnlyAuthentication(
        @Field("grant_type") grantType: String = "client_credentials"
    ): Observable<AuthenticationResult>
}
