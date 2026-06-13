package com.example.taskflow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.taskflow.R

@Composable
fun AboutScreen(onBackClick: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val locale = context.resources.configuration.locales[0]
    val isIndonesian = locale.language == "in" || locale.language == "id"

    val txtPhilosophy = if (isIndonesian) "Filosofi" else "The Philosophy"
    val txtBody = if (isIndonesian) {
        "TaskFlow dirancang untuk memberikan ruang bernapas bagi arsitek dan kreator dalam menyusun alur kerja mereka secara tenang dan terstruktur."
    } else {
        "TaskFlow is designed to give architects and creators a breathing room to organize their workflow calmly and structurally."
    }
    val txtBtnBack = if (isIndonesian) "Kembali ke Pengaturan" else "Back to Settings"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = txtPhilosophy,
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = txtBody,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
            )
        ) {
            Text(text = txtBtnBack)
        }
    }
}