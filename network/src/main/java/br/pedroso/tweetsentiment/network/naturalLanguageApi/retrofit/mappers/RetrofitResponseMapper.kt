package br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.mappers

import br.pedroso.tweetsentiment.domain.Sentiment
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.entities.response.NaturalLanguageApiResponseBody

/**
 * Created by felip on 10/03/2018.
 */
class RetrofitResponseMapper {
    companion object {
        fun retrofitResponseToDomain(responseBody: NaturalLanguageApiResponseBody): Sentiment {
            val sentimentScore = responseBody.documentSentiment.score
            return when {
                sentimentScore < -0.33f -> Sentiment.Sad
                sentimentScore > 0.33f -> Sentiment.Happy
                else -> Sentiment.Neutral
            }
        }
    }
}