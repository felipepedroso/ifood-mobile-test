package br.pedroso.tweetsentiment.features.tweetslist.usecases

import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.entities.Sentiment
import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.network.dataSources.SentimentAnalysisDataSource

class AnalyseTweetSentiment(
    private val sentimentAnalysisDataSource: SentimentAnalysisDataSource,
    private val databaseDataSource: DatabaseDataSource
) {

    suspend fun execute(tweet: Tweet) {
        if (tweet.sentiment == Sentiment.NotAnalyzed) {
            val analysedSentiment = sentimentAnalysisDataSource.analyzeSentimentFromText(tweet.text)
            databaseDataSource.updateTweetSentiment(tweet, analysedSentiment)
        }
    }
}
