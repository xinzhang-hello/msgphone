package com.example.msgphone

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.telephony.SmsMessage
import android.util.Log
import java.io.BufferedOutputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val bundle = intent?.extras
        println("test ceve")
        Log.i("SmsReceiver", intent?.action.toString())
        if (bundle != null) {
            val pdus = bundle.get("pdus") as Array<*>?
            if (pdus != null) {
                for (pdu in pdus) {
                    val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
                    val sender = smsMessage.displayOriginatingAddress
                    val message = smsMessage.messageBody
                    SendSmsTask().execute(sender, message)
                    // Handle the extracted sender and message content
                }
            }
        }
    }


    private class SendSmsTask : AsyncTask<String, Void, Void>() {
        override fun doInBackground(vararg params: String): Void? {
            val sender = params[0]
            val message = params[1]

            val url = URL("http://10.0.2.2:8080/sms")
            val connection = url.openConnection() as HttpURLConnection

            try {
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val jsonInputString = "{\"sender\":\"$sender\",\"message\":\"$message\"}"

                val os: OutputStream = BufferedOutputStream(connection.outputStream)
                os.write(jsonInputString.toByteArray())
                os.flush()

                // Check the server response if needed
                val responseCode = connection.responseCode
                // Handle the response code or other server response if required

            } finally {
                connection.disconnect()
            }

            return null
        }
    }

}