package com.example.taskflow.ui.screens

import androidx.compose.ui.graphics.Color
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0]
    val isIndonesian = locale.language == "in" || locale.language == "id"

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val txtHeadline = if (isIndonesian) "Selamat Datang\nKembali" else "Welcome\nBack"
    val txtSub = if (isIndonesian) "Masuk untuk mengelola alur kerja Anda." else "Sign in to manage your architectural workflow."
    val txtBtn = if (isIndonesian) "Masuk Sekarang" else "Sign In Now"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
            shape = RoundedCornerShape(50.dp)
        ) {
            Text(
                text = "SECURITY GATE",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = txtHeadline,
            fontSize = 42.sp,
            lineHeight = 48.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = txtSub,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        CustomLabel(text = "USERNAME")
        CustomTextField(
            value = username,
            onValueChange = { username = it },
            placeholder = if (isIndonesian) "Masukkan username" else "Enter username"
        )

        CustomLabel(text = "PASSWORD")
        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text(if (isIndonesian) "Masukkan password" else "Enter password", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f), fontSize = 14.sp) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                if (username == "admin" && password == "taskflow123") {
                    onLoginSuccess()
                } else {
                    val alertTxt = if (isIndonesian) {
                        "Username atau password salah!"
                    } else {
                        "Invalid username or password!"
                    }
                    Toast.makeText(context, alertTxt, Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = txtBtn, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}