 package com.example.nextgitter2.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nextgitter2.data.model.Notifier
import com.example.nextgitter2.databinding.ActivitySettingBinding
import com.example.nextgitter2.preference.NotifierPreference
import com.example.nextgitter2.receiver.NotifierReceiver

 class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var notifier: Notifier
    private lateinit var notifierReceiver: NotifierReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val notifierPreference = NotifierPreference(this)
        binding.switching.isChecked = notifierPreference.getNotifier().isNotified

        notifierReceiver = NotifierReceiver()
        binding.switching.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                saveNotifierState(true)
                notifierReceiver.setRepeatNotifier(this, "RepeatingNotifier", "09:00", "Click aku, mari menemukan hal baru bersamaku!")
            } else {
                saveNotifierState(false)
                notifierReceiver.cancelNotifier(this)
            }
        }
    }

     private fun saveNotifierState(state: Boolean) {
        val notifierPreference = NotifierPreference(this)
         notifier = Notifier()

         notifier.isNotified = state
         notifierPreference.setNotifier(notifier)
     }
 }