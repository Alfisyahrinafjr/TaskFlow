package com.example.taskflow.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskflow.R
import com.example.taskflow.ui.viewmodel.TaskViewModel
import com.example.taskflow.domain.model.Task

object DashboardColors {
    val BrandBlue = Color(0xFF3B82F6)
    val HighPriorityRed = Color(0xFFDC3545)
    val MediumPriorityBrown = Color(0xFFE67E22)
    val LowPriorityBlue = Color(0xFF0D6EFD)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    viewModel: TaskViewModel
) {
    val tasks by viewModel.tasksState.collectAsState()
    val totalTasks = tasks.size
    val completedTasks = tasks.count { it.isCompleted }
    val efficiencyPercentage = if (totalTasks > 0) (completedTasks.toFloat() / totalTasks.toFloat()) else 0f

    val kuliahCount = tasks.count { it.category.equals("Kuliah", ignoreCase = true) }
    val pribadiCount = tasks.count { it.category.equals("Pribadi", ignoreCase = true) }
    val organisasiCount = tasks.count { it.category.equals("Organisasi", ignoreCase = true) }
    val lainnyaCount = tasks.count { it.category.equals("Lainnya", ignoreCase = true) }

    val highPriorityCount = tasks.count { it.priority.equals("HIGH", ignoreCase = true) }
    val mediumPriorityCount = tasks.count { it.priority.equals("MEDIUM", ignoreCase = true) }
    val lowPriorityCount = tasks.count { it.priority.equals("LOW", ignoreCase = true) }

    val context = androidx.compose.ui.platform.LocalContext.current
    val locale = context.resources.configuration.locales[0]
    val isIndonesian = locale.language == "in" || locale.language == "id"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "TASKFLOW",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(start = 8.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* Handle menu */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = MaterialTheme.colorScheme.onBackground)
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🧑‍💼")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column {
                    Text(
                        text = stringResource(id = R.string.analytics_dashboard),
                        color = DashboardColors.BrandBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Text(
                        text = stringResource(id = R.string.weekly_impact),
                        fontWeight = FontWeight.Black,
                        fontSize = 40.sp,
                        lineHeight = 44.sp,
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(top = 4.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x1A888888))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                Icons.Default.DateRange,
                                contentDescription = null,
                                tint = DashboardColors.BrandBlue,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "Oct 12 - Oct 19",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            item {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth(),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x1A888888))
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(200.dp)) {
                            CircularProgressIndicatorCustom(
                                progress = efficiencyPercentage,
                                size = 180.dp,
                                strokeWidth = 18.dp,
                                unfilledColor = MaterialTheme.colorScheme.background
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    "${(efficiencyPercentage * 100).toInt()}%",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 50.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    "EFFICIENCY",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    letterSpacing = 1.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = stringResource(id = R.string.total_completed),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        val txtTasksFinished = if (isIndonesian) "$completedTasks dari $totalTasks tugas diselesaikan" else "$completedTasks of $totalTasks tasks finished"
                        Text(
                            text = txtTasksFinished,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            item {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(24.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x1A888888)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.tasks_by_category),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Icon(Icons.Default.MoreHoriz, contentDescription = "More", tint = Color.Gray)
                        }
                        Spacer(Modifier.height(24.dp))

                        CategoryRow(if (isIndonesian) "Kuliah" else "College", kuliahCount)
                        HorizontalDivider(color = Color(0x1A888888), modifier = Modifier.padding(vertical = 12.dp))
                        CategoryRow(if (isIndonesian) "Pribadi" else "Personal", pribadiCount)
                        HorizontalDivider(color = Color(0x1A888888), modifier = Modifier.padding(vertical = 12.dp))
                        CategoryRow(if (isIndonesian) "Organisasi" else "Organization", organisasiCount)
                        HorizontalDivider(color = Color(0x1A888888), modifier = Modifier.padding(vertical = 12.dp))
                        CategoryRow(if (isIndonesian) "Lainnya" else "Others", lainnyaCount)
                    }
                }
            }

            item {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth(),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x1A888888))
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            text = stringResource(id = R.string.priority_distribution),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(Modifier.height(24.dp))

                        val lblTasks = if (isIndonesian) "TUGAS" else "TASKS"
                        PriorityBar(if (isIndonesian) "PRIORITAS TINGGI" else "HIGH PRIORITY", highPriorityCount, totalTasks, DashboardColors.HighPriorityRed, lblTasks, MaterialTheme.colorScheme.background)
                        Spacer(Modifier.height(16.dp))
                        PriorityBar(if (isIndonesian) "PRIORITAS SEDANG" else "MEDIUM PRIORITY", mediumPriorityCount, totalTasks, DashboardColors.MediumPriorityBrown, lblTasks, MaterialTheme.colorScheme.background)
                        Spacer(Modifier.height(16.dp))
                        PriorityBar(if (isIndonesian) "PRIORITAS RENDAH" else "LOW PRIORITY", lowPriorityCount, totalTasks, DashboardColors.LowPriorityBlue, lblTasks, MaterialTheme.colorScheme.background)
                    }
                }
            }

            item {
                Surface(
                    color = DashboardColors.BrandBlue,
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Icon(Icons.Default.Stars, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
                        Spacer(Modifier.height(16.dp))

                        val txtFocusTitle = if (isIndonesian) "Puncak Fokus\nTerdeteksi." else "Focus Peak\nDetected."
                        val txtFocusSub = if (isIndonesian)
                            "Anda menyelesaikan lebih banyak tugas antara pukul 09.00 dan 11.00. Jadwalkan kerja mendalam pada waktu tersebut."
                        else "You complete more tasks between 9:00 AM and 11:00 AM. Schedule deep work then."

                        Text(text = txtFocusTitle, fontWeight = FontWeight.Bold, fontSize = 28.sp, lineHeight = 32.sp, color = Color.White)
                        Spacer(Modifier.height(12.dp))
                        Text(text = txtFocusSub, fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f), lineHeight = 20.sp)
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}


@Composable
fun CircularProgressIndicatorCustom(progress: Float, size: Dp, strokeWidth: Dp, unfilledColor: Color) {
    Canvas(modifier = Modifier.size(size)) {
        drawCircle(color = unfilledColor, style = Stroke(width = strokeWidth.toPx()))
        drawArc(color = DashboardColors.BrandBlue, startAngle = -90f, sweepAngle = 360 * progress, useCenter = false, style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round))
    }
}

@Composable
fun CategoryRow(name: String, taskCount: Int) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(name, fontWeight = FontWeight.Medium, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
        Text("$taskCount ${if (name == "Kuliah" || name == "Pribadi" || name == "Organisasi" || name == "Lainnya") "TUGAS" else "TASKS"}", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = DashboardColors.BrandBlue, letterSpacing = 0.5.sp)
    }
}

@Composable
fun PriorityBar(label: String, current: Int, total: Int, color: Color, suffix: String, trackColor: Color) {
    val progressFactor = if (total > 0) current.toFloat() / total.toFloat() else 0f
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(label, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = color, letterSpacing = 0.5.sp)
            Text("$current $suffix", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = MaterialTheme.colorScheme.onBackground, letterSpacing = 0.5.sp)
        }
        Spacer(Modifier.height(8.dp))
        Box(modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape).background(trackColor)) {
            Box(modifier = Modifier.fillMaxWidth(progressFactor).fillMaxHeight().clip(CircleShape).background(color))
        }
    }
}