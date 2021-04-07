package com.example.nextgitter2.preference

import android.content.Context
import com.example.nextgitter2.data.model.Notifier

class NotifierPreference(context: Context) {
    companion object {
        const val NOTIFIER_NAME = "notifier_name"
        private const val NOTIFIER = "notifier"
    }

    private val preference = context.getSharedPreferences(NOTIFIER_NAME, Context.MODE_PRIVATE)

    fun setNotifier(value: Notifier) {
        val edit = preference.edit()
        edit.putBoolean(NOTIFIER, value.isNotified)
        edit.apply()
    }

    fun getNotifier(): Notifier {
        val state = Notifier()
        state.isNotified = preference.getBoolean(NOTIFIER, false)
        return state
    }
}