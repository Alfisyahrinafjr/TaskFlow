package com.example.taskflow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AboutScreen(onBackClick: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val locale = context.resources.configuration.locales[0]
    val isIndonesian = locale.language == "in" || locale.language == "id"

    val scrollState = rememberScrollState()

    val txtPhilosophy = if (isIndonesian) "Filosofi\nDesain." else "Design\nPhilosophy."
    val txtBtnBack = if (isIndonesian) "Kembali ke Pengaturan" else "Back to Settings"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = txtPhilosophy,
            fontSize = 42.sp,
            lineHeight = 48.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // --- SEKSI 1: ESENSI UTAMA ---
            Text(
                text = if (isIndonesian) "1. Esensi Nama: TaskFlow" else "1. The Essence of TaskFlow",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = if (isIndonesian) {
                    "TaskFlow bukan sekadar alat pencatat digital biasa. Nama ini lahir dari gabungan dua konsep: 'Task' (Tugas/Tanggung Jawab) dan 'Flow' (Kondisi Psikologis Fokus Total). Kami mengadopsi teori psikologi 'Flow State' oleh Mihaly Csikszentmihalyi, di mana seseorang menjadi sangat tenggelam dan produktif dalam aktivitasnya. Aplikasi ini dirancang sebagai jembatan kognitif agar pengguna dapat mengalirkan energi produktif mereka tanpa hambatan friksi antarmuka."
                } else {
                    "TaskFlow is not just a digital note-taking tool. The name stems from two concepts: 'Task' and 'Flow' (The Psychological State of Absolute Focus). We adopt Mihaly Csikszentmihalyi's 'Flow State' theory, where a person becomes fully immersed and productive. This app is architected as a cognitive bridge so users can channel their productive energy without any interface friction."
                },
                fontSize = 14.sp,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            // --- SEKSI 2: FILOSOFI WARNA ---
            Text(
                text = if (isIndonesian) "2. Psikologi Warna & Estetika Antarmuka" else "2. Color Psychology & Interface Aesthetics",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = if (isIndonesian) {
                    "Mengurangi kelelahan visual (visual fatigue) mahasiswa dalam menatap layar adalah prioritas kami. Oleh karena itu, TaskFlow menggunakan palet warna khusus:\n" +
                            "• Deep Charcoal / Dark Slate (Background): Melambangkan stabilitas, kedalaman akademis, dan fokus tanpa distraksi.\n" +
                            "• Emerald Green (Aksen/Success): Dipilih sebagai representasi keberhasilan, penyelesaian misi, dan energi positif yang menenangkan mental pengguna setelah menyelesaikan tugas berat."
                } else {
                    "Reducing visual fatigue for students looking at screens is our priority. Therefore, TaskFlow uses a tailored color palette:\n" +
                            "• Deep Charcoal / Dark Slate (Background): Represents stability, academic depth, and distraction-free focus.\n" +
                            "• Emerald Green (Accent/Success): Chosen to signify mission completion and a calming sense of achievement after rigorous technical execution."
                },
                fontSize = 14.sp,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            // --- SEKSI 3: TIPOGRAFI & STRUKTUR ---
            Text(
                text = if (isIndonesian) "3. Tipografi Editorial & Struktur Tegas" else "3. Editorial Typography & Bold Structures",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = if (isIndonesian) {
                    "Berbeda dengan aplikasi manajemen tugas kasual yang cenderung ceria, TaskFlow mengambil pendekatan 'Editorial Brutalism' yang tegas dan maskulin. Penggunaan ukuran font yang besar (Bold Display Typography) dipadukan dengan tata letak kartu bersudut minimalis (12.dp - 20.dp). Hal ini menciptakan kesan hierarki informasi yang absolut, serius, profesional, dan andal—mencerminkan disiplin tinggi yang dibutuhkan dalam dunia rekayasa teknologi."
                } else {
                    "Unlike casual task managers that lean cheerful, TaskFlow adopts a firm 'Editorial Brutalism' approach. The bold display typography paired with structured card layouts (12.dp - 20.dp) establishes an absolute informational hierarchy. It feels serious, professional, and reliable—mirroring the strict discipline required in software engineering."
                },
                fontSize = 14.sp,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            // --- SEKSI 4: INTEGRASI STRATEGIS ---
            Text(
                text = if (isIndonesian) "4. Arsitektur Terintegrasi Standar Industri" else "4. Industry-Standard Integrated Architecture",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = if (isIndonesian) {
                    "Filosofi fungsionalitas TaskFlow didasarkan pada efisiensi operasional mutlak. Dengan mengintegrasikan sistem lokal database (Room) untuk ketahanan data offline dan API jaringan (Retrofit) untuk sinkronisasi eksternal hari libur nasional, aplikasi ini mencerminkan arsitektur berstandar industri. Setiap elemen UI yang diletakkan memiliki tujuan fungsionalitas yang jelas untuk mendukung manajemen waktu pengguna secara nyata."
                } else {
                    "TaskFlow's functional philosophy is rooted in operational efficiency. By integrating a local database (Room) for offline resilience and network API (Retrofit) for holiday tracking, this app reflects an industry-standard architecture. Every single UI element serves a practical purpose in scaling real-world productivity metrics."
                },
                fontSize = 14.sp,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
            )
        ) {
            Text(text = txtBtnBack, fontWeight = FontWeight.Bold)
        }
    }
}