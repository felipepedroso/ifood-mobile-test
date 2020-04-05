package br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit

import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.entities.request.NaturalLanguageApiRequestBody
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.entities.response.NaturalLanguageApiResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface NaturalLanguageApiService {
    @POST("/v1/documents:analyzeSentiment")
    suspend fun analyzeSentiment(@Body requestBody: NaturalLanguageApiRequestBody): NaturalLanguageApiResponseBody
}
