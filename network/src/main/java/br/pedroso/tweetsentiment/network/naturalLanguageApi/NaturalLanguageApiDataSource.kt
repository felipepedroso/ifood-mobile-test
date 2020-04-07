package br.pedroso.tweetsentiment.network.naturalLanguageApi

import br.pedroso.tweetsentiment.domain.entities.Sentiment
import br.pedroso.tweetsentiment.domain.network.dataSources.SentimentAnalysisDataSource
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.NaturalLanguageApiService
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.entities.request.Document
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.entities.request.NaturalLanguageApiRequestBody
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.mappers.RetrofitResponseMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class NaturalLanguageApiDataSource(
    private val dispatcher: CoroutineDispatcher,
    private val naturalLanguageApiService: NaturalLanguageApiService
) : SentimentAnalysisDataSource {
    override suspend fun analyzeSentimentFromText(text: String): Sentiment {
        return withContext(dispatcher) {
            val document = Document(text)
            val requestBody = NaturalLanguageApiRequestBody(document)
            val response = naturalLanguageApiService.analyzeSentiment(requestBody)

            RetrofitResponseMapper.retrofitResponseToDomain(response)
        }
    }
}
