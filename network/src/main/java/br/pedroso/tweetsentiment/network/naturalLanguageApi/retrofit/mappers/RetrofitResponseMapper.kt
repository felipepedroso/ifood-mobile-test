package br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.mappers

import br.pedroso.tweetsentiment.domain.entities.Sentiment
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.entities.response.NaturalLanguageApiResponseBody

object RetrofitResponseMapper {
    private const val NEUTRAL_SENTIMENT_UPPER_BOUNDARY = 0.33f
    private const val NEUTRAL_SENTIMENT_LOWER_BOUNDARY = 0.33f
    fun retrofitResponseToDomain(responseBody: NaturalLanguageApiResponseBody): Sentiment {
        val sentimentScore = responseBody.documentSentiment.score
        return when {
            sentimentScore < NEUTRAL_SENTIMENT_LOWER_BOUNDARY -> Sentiment.Sad
            sentimentScore > NEUTRAL_SENTIMENT_UPPER_BOUNDARY -> Sentiment.Happy
            else -> Sentiment.Neutral
        }
    }
}
