package br.pedroso.tweetsentiment.app.features.tweetsList.utils

import android.content.Context
import br.pedroso.tweetsentiment.R
import khronos.Dates
import khronos.beginningOfYear
import khronos.hours
import khronos.minutes
import khronos.rangeTo
import khronos.week
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TweetTimestampFormatter {
    companion object {
        private const val FORMAT_GENERIC_DATE = "dd MMM yy"
        private const val FORMAT_CURRENT_YEAR = "dd MMM"

        private const val SECONDS_IN_A_MINUTE = 60
        private const val SECONDS_IN_AN_HOUR = SECONDS_IN_A_MINUTE * 60
        private const val SECONDS_IN_A_DAY = SECONDS_IN_AN_HOUR * 24
        private const val MILLISECONDS_IN_A_SECOND = 1000
        private const val MINUTES_AMOUNT_TO_FORMAT_AS_LAST_HOUR = SECONDS_IN_A_MINUTE
        private const val HOURS_AMOUNT_TO_FORMAT_AS_LAST_24_HOURS = 24
        private const val WEEKS_AMOUNT_TO_FORMAT_AS_LAST_WEEK = 1

        fun format(context: Context, timestamp: Date): String {
            val now = Dates.today

            return when (timestamp) {
                in MINUTES_AMOUNT_TO_FORMAT_AS_LAST_HOUR.minutes.ago..now -> formatLastHourDate(
                    context,
                    timestamp
                )
                in HOURS_AMOUNT_TO_FORMAT_AS_LAST_24_HOURS.hours.ago..now -> formatLast24HoursDate(
                    context,
                    timestamp
                )
                in WEEKS_AMOUNT_TO_FORMAT_AS_LAST_WEEK.week.ago..Dates.today -> formatLastWeekDate(
                    context,
                    timestamp
                )
                in Dates.today.beginningOfYear..Dates.today -> formatCurrentYearDate(timestamp)
                else -> formatGenericDate(timestamp)
            }
        }

        private fun formatLastHourDate(context: Context, timestamp: Date): String {
            val timespanFromNow = calculateTimeSpanFromNowInSeconds(timestamp) / SECONDS_IN_A_MINUTE

            return context.getString(R.string.format_last_hour_date, timespanFromNow)
        }

        private fun calculateTimeSpanFromNowInSeconds(timestamp: Date): Long {
            return (Dates.today.time - timestamp.time) / MILLISECONDS_IN_A_SECOND
        }

        private fun formatLast24HoursDate(context: Context, timestamp: Date): String {
            val timespanFromNow = calculateTimeSpanFromNowInSeconds(timestamp) / SECONDS_IN_AN_HOUR

            return context.getString(R.string.format_last_24_hours_date, timespanFromNow)
        }

        private fun formatLastWeekDate(context: Context, timestamp: Date): String {
            val timespanFromNow = calculateTimeSpanFromNowInSeconds(timestamp) / SECONDS_IN_A_DAY

            return context.getString(R.string.format_last_week_date, timespanFromNow)
        }

        private fun formatGenericDate(timestamp: Date): String {
            val formatter = SimpleDateFormat(FORMAT_GENERIC_DATE, Locale.getDefault())
            return formatter.format(timestamp)
        }

        private fun formatCurrentYearDate(timestamp: Date): String {
            val formatter = SimpleDateFormat(FORMAT_CURRENT_YEAR, Locale.getDefault())
            return formatter.format(timestamp)
        }
    }
}
