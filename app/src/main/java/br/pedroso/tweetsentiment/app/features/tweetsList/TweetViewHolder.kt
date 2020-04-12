package br.pedroso.tweetsentiment.app.features.tweetsList

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.pedroso.tweetsentiment.app.features.tweetsList.utils.resourceColor
import br.pedroso.tweetsentiment.app.features.tweetsList.utils.resourceIcon
import br.pedroso.tweetsentiment.domain.entities.Sentiment
import br.pedroso.tweetsentiment.domain.entities.Tweet
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tweet_content.buttonAnalyzeSentiment
import kotlinx.android.synthetic.main.item_tweet_content.textViewTweetContent
import kotlinx.android.synthetic.main.item_tweet_content.textViewTweetTimestamp
import kotlinx.android.synthetic.main.item_tweet_content.tweetSentimentChip
import net.danlew.android.joda.DateUtils

class TweetViewHolder(
    override val containerView: View,
    private val buttonAnalyzeSentimentClickListener: (Tweet) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(tweet: Tweet) {
        with(tweet) {
            textViewTweetContent.text = text
            textViewTweetTimestamp.text = DateUtils.getRelativeTimeSpanString(
                containerView.context,
                creationTimestamp,
                DateUtils.FORMAT_ABBREV_RELATIVE
            )

            tweetSentimentChip.apply {
                text = sentiment.name
                setChipBackgroundColorResource(sentiment.resourceColor)
                setChipIconResource(sentiment.resourceIcon)
                visibility = View.INVISIBLE
            }

            if (sentiment == Sentiment.NotAnalyzed) {
                buttonAnalyzeSentiment.setOnClickListener {
                    buttonAnalyzeSentimentClickListener(tweet)
                }
                buttonAnalyzeSentiment.visibility = View.VISIBLE
            } else {
                buttonAnalyzeSentiment.visibility =                    View.INVISIBLE
                tweetSentimentChip.visibility = View.VISIBLE
            }
        }
    }
}