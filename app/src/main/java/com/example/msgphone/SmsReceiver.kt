package com.example.msgphone

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class SmsReceiver : BroadcastReceiver() {

    val keyWordsList = listOf("上海交警,未按规定停放")
    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.extras
        Log.i("SmsReceiver", intent?.action.toString())
        if (bundle != null) {
            val pdus = bundle.get("pdus") as Array<*>?
            if (pdus != null) {
                for (pdu in pdus) {
                    val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
                    val sender = smsMessage.displayOriginatingAddress
                    val message = smsMessage.messageBody
                    keyWordsList.map { key ->
                        for (word in key.split(",")) {
                            if (!message.contains(word)) {
                                return
                            }
                        }
                        val data =
                            Data.Builder().putString("message", message).putString("sender", sender)
                                .putLong("recTime", System.currentTimeMillis()).build()
                        val request = OneTimeWorkRequestBuilder<SmsTriggerTask>().setInputData(data)
                            .build()
                        WorkManager.getInstance(context!!).enqueue(request)
                    }
                }
            }
        }
    }
}