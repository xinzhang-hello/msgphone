package com.example.msgphone

import android.R
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        assertEquals("com.example.msgphone", appContext.packageName)
//    }

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testSmsReceiver() {
        // Simulate receiving an SMS
        val sender = "123456"
        val message = "Test message"

        val intent = Intent("android.provider.Telephony.SMS_RECEIVED").apply {
            putExtra("pdus", arrayOf(SmsMessage.createFromPdu("test pdu data".toByteArray())))
        }

        // Trigger the broadcast
        ApplicationProvider.getApplicationContext<Context>().sendBroadcast(intent)

        // Now, you can check if your app has processed the received SMS appropriately
        // For example, you can use Espresso to verify UI changes or other actions in response to SMS reception

        // Example: Check if a TextView displays the received message
    }

}