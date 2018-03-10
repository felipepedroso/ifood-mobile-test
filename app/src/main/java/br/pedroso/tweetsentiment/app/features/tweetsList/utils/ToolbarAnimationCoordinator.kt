package br.pedroso.tweetsentiment.app.features.tweetsList.utils

import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.widget.LinearLayout
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by felip on 15/02/2018.
 */
class ToolbarAnimationCoordinator(
        val profileImageView: CircleImageView,
        val textViewName: TextView,
        val toolbar: CollapsingToolbarLayout,
        val linearLayoutUserDetailsContainer: LinearLayout) : AppBarLayout.OnOffsetChangedListener {

    companion object {
        private var isShowingProfilePicture = true
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (verticalOffset == 0) {
            if (!isShowingProfilePicture) {
                profileImageView.animate()
                        .scaleY(1f).scaleX(1f)
                        .start()

                linearLayoutUserDetailsContainer.animate()
                        .scaleY(1f).scaleX(1f)
                        .start()
                isShowingProfilePicture = true
            }

            toolbar.title = ""
        } else if (Math.abs(verticalOffset) == appBarLayout.totalScrollRange) {
            if (isShowingProfilePicture) {
                profileImageView.animate()
                        .scaleY(0f).scaleX(0f)
                        .setDuration(100)
                        .start()

                linearLayoutUserDetailsContainer.animate()
                        .scaleY(0f).scaleX(0f)
                        .setDuration(100)
                        .start()
                isShowingProfilePicture = false
            }
            toolbar.title = textViewName.text
        }

    }

}