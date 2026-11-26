package com.leksin.app.ui.screens.child

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object StreakManager {
    private const val PREFS_NAME = "leksin_streak_prefs"
    private const val KEY_CURRENT_STREAK = "current_streak"
    private const val KEY_LONGEST_STREAK = "longest_streak"
    private const val KEY_LAST_DATE = "last_activity_date"
    private const val KEY_STREAK_DATES = "streak_dates_set"

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun recordDailyActivity(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val todayStr = dateFormat.format(Date())

        val lastDateStr = prefs.getString(KEY_LAST_DATE, null)
        val streakDates = prefs.getStringSet(KEY_STREAK_DATES, HashSet())?.toHashSet() ?: HashSet()

        if (lastDateStr == todayStr) {
            streakDates.add(todayStr)
            prefs.edit().putStringSet(KEY_STREAK_DATES, streakDates).apply()
            return
        }

        var currentStreak = prefs.getInt(KEY_CURRENT_STREAK, 0)
        var longestStreak = prefs.getInt(KEY_LONGEST_STREAK, 0)

        if (lastDateStr != null) {
            try {
                val lastCal = Calendar.getInstance().apply {
                    val parsed = dateFormat.parse(lastDateStr)
                    if (parsed != null) time = parsed
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                val todayCal = Calendar.getInstance().apply {
                    time = Date()
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }

                val diffMs = todayCal.timeInMillis - lastCal.timeInMillis
                val daysBetween = diffMs / (1000 * 60 * 60 * 24)

                if (daysBetween == 1L) {
                    currentStreak += 1
                } else if (daysBetween > 1L) {
                    currentStreak = 1
                }
            } catch (e: Exception) {
                currentStreak = 1
            }
        } else {
            currentStreak = 1
        }

        if (currentStreak > longestStreak) {
            longestStreak = currentStreak
        }

        streakDates.add(todayStr)

        prefs.edit()
            .putInt(KEY_CURRENT_STREAK, currentStreak)
            .putInt(KEY_LONGEST_STREAK, longestStreak)
            .putString(KEY_LAST_DATE, todayStr)
            .putStringSet(KEY_STREAK_DATES, streakDates)
            .apply()
    }

    fun getCurrentStreak(context: Context): Int {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_CURRENT_STREAK, 0)
    }

    fun getLongestStreak(context: Context): Int {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_LONGEST_STREAK, 0)
    }

    fun getStreakDates(context: Context): Set<String> {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getStringSet(KEY_STREAK_DATES, emptySet()) ?: emptySet()
    }
}
