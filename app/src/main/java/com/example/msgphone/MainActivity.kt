package com.example.msgphone

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.msgphone.ui.theme.MsgphoneTheme

class MainActivity : ComponentActivity() {

    private val permissions = arrayOf(
        Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()

        setContent {
            MsgphoneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Test Workd")
                }
            }
        }
    }


    private fun requestPermissions() {
        //check the API level
        //filter permissions array in order to get permissions that have not been granted
        val notGrantedPermissions = permissions.filterNot { permission ->
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }
        if (notGrantedPermissions.isNotEmpty()) {
            //check if permission was previously denied and return a boolean value
            val showRationale = notGrantedPermissions.any { permission ->
                ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
            }
            //if true, explain to user why granting this permission is important
            if (showRationale) {
                smsPermissions.launch(notGrantedPermissions.toTypedArray())
            }
        }
    }

    private val smsPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionMap ->
            if (!permissionMap.all { it.value }) {
                Toast.makeText(this, "SMS permissions not granted!", Toast.LENGTH_SHORT).show()
            }
        }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Hello $name!", modifier = modifier
        )

        Text(
            text = "Hello Row 2$name!", modifier = modifier
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MsgphoneTheme {
        Greeting("Android")
    }
}
