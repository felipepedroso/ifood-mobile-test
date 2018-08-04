package br.pedroso.tweetsentiment.app.tweetsList.utils

import br.pedroso.tweetsentiment.R
import br.pedroso.tweetsentiment.domain.Sentiment


val Sentiment.resourceColor: Int
    get() = when (this) {
        Sentiment.Happy -> R.color.colorSentimentHappy
        Sentiment.Sad -> R.color.colorSentimentSad
        else -> R.color.colorSentimentNeutral
    }


val Sentiment.resourceIcon: Int
    get() = when (this) {
        Sentiment.Happy -> R.drawable.ic_sentiment_happy
        Sentiment.Sad -> R.drawable.ic_sentiment_sad
        Sentiment.NotAnalyzed -> R.drawable.ic_sentiment_not_analyzed
        else -> R.drawable.ic_sentiment_neutral
    }