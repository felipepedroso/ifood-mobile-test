package br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit

import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.entities.request.NaturalLanguageApiRequestBody
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.entities.response.NaturalLanguageApiResponseBody
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface NaturalLanguageApiService {
    @POST("/v1/documents:analyzeSentiment")
    fun analyzeSentiment(@Body requestBody: NaturalLanguageApiRequestBody): Observable<NaturalLanguageApiResponseBody>
}
