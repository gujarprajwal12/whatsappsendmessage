package com.example.whatsappnumber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import java.net.URLEncoder


// MainActivity.kt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhatsAppMessageSender()
        }
    }
}

@Composable
fun WhatsAppMessageSender() {
    val context = LocalContext.current
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Enter phone number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val number = "+91"+phoneNumber.text.trim()
                if (number.isNotEmpty()) {
                    sendMessageOnWhatsApp(context, number)
                } else {
                    Toast.makeText(context, "Please enter a phone number", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Send on WhatsApp")
        }



        Text(
            text = "Developed by Prajwal Gujar",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )


    }


}

// old code
//fun sendMessageOnWhatsApp(context: android.content.Context, phoneNumber: String) {
//    try {
//        val message = "Hello " // Customize the message
//        val url = "https://api.whatsapp.com/send?phone=$phoneNumber&text=" + URLEncoder.encode(message, "UTF-8")
//        val intent = Intent(Intent.ACTION_VIEW)
//        intent.data = Uri.parse(url)
//        intent.setPackage("com.whatsapp")
//
//        if (intent.resolveActivity(context.packageManager) != null) {
//            context.startActivity(intent)
//        } else {
//            Toast.makeText(context, "WhatsApp not installed", Toast.LENGTH_SHORT).show()
//        }
//    } catch (e: Exception) {
//        e.printStackTrace()
//        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
//    }
//}

// new code
fun sendMessageOnWhatsApp(context: android.content.Context, phoneNumber: String) {
    try {
        val message = "Hello" // Customize the message
        val url = "https://api.whatsapp.com/send?phone=$phoneNumber&text=" + URLEncoder.encode(message, "UTF-8")
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
            setPackage("com.whatsapp")
        }

        // Check if WhatsApp is installed
        val packageManager = context.packageManager
        val isWhatsAppInstalled = try {
            packageManager.getPackageInfo("com.whatsapp", 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }

        if (isWhatsAppInstalled) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "WhatsApp is not installed", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}




@Preview(showBackground = true)
@Composable
internal fun WhatsAppMessageSenderPreview() {
    WhatsAppMessageSender()
}
