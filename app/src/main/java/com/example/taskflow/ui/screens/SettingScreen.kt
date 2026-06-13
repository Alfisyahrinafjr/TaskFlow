package com.example.taskflow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    currentLanguage: String,
    onLanguageChange: (String) -> Unit,
    onAboutClick: () -> Unit
) {
    val context = LocalContext.current

    val configuration = context.resources.configuration
    val locale = configuration.locales[0]
    val isIndonesian = locale.language == "in" || locale.language == "id"

    var isLanguageMenuExpanded by remember { mutableStateOf(false) }

    val txtTitle = if (isIndonesian) "PENGATURAN" else "SETTINGS"
    val txtPrefTitle = if (isIndonesian) "Preferensi" else "Preferences"
    val txtPrefSub = if (isIndonesian) "Tentukan preferensi teknis Anda" else "Specify your technical preferences"
    val txtSecAppearance = if (isIndonesian) "TAMPILAN" else "APPEARANCE"
    val txtLblDark = if (isIndonesian) "Mode Gelap" else "Dark Mode"
    val txtSubDark = if (isIndonesian) "Sesuaikan tema visual" else "Adjust the visual theme"
    val txtLblLang = if (isIndonesian) "Bahasa" else "Language"
    val txtSubLang = if (isIndonesian) "Pilih bahasa sistem" else "Select system language"
    val txtSecSystem = if (isIndonesian) "SISTEM & TENTANG" else "SYSTEM & ABOUT"
    val txtLblAbout = if (isIndonesian) "Tentang TaskFlow" else "About TaskFlow"
    val txtSubAbout = if (isIndonesian) "Informasi arsitektur aplikasi" else "App architecture details"

    val updateLanguage: (String) -> Unit = { langCode ->
        val newLocale = Locale(langCode)
        Locale.setDefault(newLocale)

        val resources = context.resources
        val config = resources.configuration
        config.setLocale(newLocale)

        context.createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)

        onLanguageChange(if (langCode == "id") "Indonesia" else "English (US)")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = txtTitle, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground) },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = MaterialTheme.colorScheme.onBackground)
                    }
                },
                actions = {
                    Box(modifier = Modifier.padding(end = 16.dp).size(36.dp).clip(CircleShape).background(Color.LightGray), contentAlignment = Alignment.Center) {
                        Text("🧑‍💼")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 20.dp, vertical = 16.dp)) {

            Text(text = txtPrefTitle, fontWeight = FontWeight.Bold, fontSize = 32.sp, color = MaterialTheme.colorScheme.onBackground)
            Text(text = txtPrefSub, fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = txtSecAppearance, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color.Gray, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(12.dp))

            CardSettingItem(cardColor = MaterialTheme.colorScheme.surface) {
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.background), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.NightsStay, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = txtLblDark, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
                        Text(text = txtSubDark, fontSize = 12.sp, color = Color.Gray)
                    }
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { onDarkModeChange(it) },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = MaterialTheme.colorScheme.primary)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Card: Language
            CardSettingItem(cardColor = MaterialTheme.colorScheme.surface) {
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.background), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Language, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = txtLblLang, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
                        Text(text = txtSubLang, fontSize = 12.sp, color = Color.Gray)
                    }
                    Box {
                        Button(
                            onClick = { isLanguageMenuExpanded = true },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(text = currentLanguage, color = MaterialTheme.colorScheme.onBackground, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        }
                        DropdownMenu(
                            expanded = isLanguageMenuExpanded,
                            onDismissRequest = { isLanguageMenuExpanded = false },
                            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                        ) {
                            DropdownMenuItem(
                                text = { Text("English (US)", color = MaterialTheme.colorScheme.onBackground) },
                                onClick = {
                                    updateLanguage("en")
                                    isLanguageMenuExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Indonesia", color = MaterialTheme.colorScheme.onBackground) },
                                onClick = {
                                    updateLanguage("id")
                                    isLanguageMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = txtSecSystem, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color.Gray, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(12.dp))

            // Card: About
            CardSettingItem(cardColor = MaterialTheme.colorScheme.surface, modifier = Modifier.clickable { onAboutClick() }) {
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(Color(0xFFFFEAD2)), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFFE67E22))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = txtLblAbout, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
                        Text(text = txtSubAbout, fontSize = 12.sp, color = Color.Gray)
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.LightGray)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), contentAlignment = Alignment.Center) {
                Text(text = "TaskFlow Architecture & Design © 2026", fontSize = 11.sp, color = Color.LightGray)
            }
        }
    }
}

@Composable
fun CardSettingItem(cardColor: Color, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = cardColor,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x1A888888))
    ) {
        content()
    }
}