package com.example.msgphone

import android.content.Context
import android.media.AudioAttributes
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters

class SmsTriggerTask(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    private val handler = Handler(Looper.getMainLooper())

    @RequiresApi(Build.VERSION_CODES.S)
    override fun doWork(): Result {
        val ringtoneUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        val ringtone: Ringtone = RingtoneManager.getRingtone(applicationContext, ringtoneUri)

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        // Set the audio attributes on the ringtone
        ringtone.audioAttributes = audioAttributes

        ringtone.play()

        handler.postDelayed({
            if (ringtone.isPlaying) {
                ringtone.stop()
            }
        }, 5000)
        return Result.success()
    }
}