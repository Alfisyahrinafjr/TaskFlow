package com.example.taskflow.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskflow.R
import com.example.taskflow.domain.model.Task
import com.example.taskflow.ui.viewmodel.TaskViewModel

val DynamicBrandBlue = Color(0xFF3B82F6)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFlowHomeScreen(
    viewModel: TaskViewModel,
    onTaskClick: (String) -> Unit,
    onAddTaskClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val locale = context.resources.configuration.locales[0]
    val isIndonesian = locale.language == "in" || locale.language == "id"

    var selectedFilter by remember { mutableStateOf("ALL") }
    val masterTaskList by viewModel.tasksState.collectAsState()

    val filteredTasks = remember(selectedFilter, masterTaskList) {
        if (selectedFilter == "ALL") {
            masterTaskList
        } else {
            masterTaskList.filter { it.priority.uppercase() == selectedFilter }
        }
    }

    val txtSearchPlaceholder = if (isIndonesian) "Cari alur kerja arsitektur Anda..." else "Search your architectural flow..."
    val txtNoTask = if (isIndonesian) "Belum ada tugas yang dirancang." else "No tasks architected yet."
    val txtCreateTask = if (isIndonesian) "Buat Tugas Baru" else "Create New Task"

    val tabAll = if (isIndonesian) "Semua Status" else "All Status"
    val tabHigh = if (isIndonesian) "Prioritas Tinggi" else "High Priority"
    val tabMedium = if (isIndonesian) "Prioritas Sedang" else "Medium Priority"
    val tabLow = if (isIndonesian) "Prioritas Rendah" else "Low Priority"

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text(text = txtSearchPlaceholder, color = Color.Gray, fontSize = 15.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item { FilterTabButton(text = tabAll, isSelected = selectedFilter == "ALL", onClick = { selectedFilter = "ALL" }) }
                    item { FilterTabButton(text = tabHigh, isSelected = selectedFilter == "HIGH", onClick = { selectedFilter = "HIGH" }) }
                    item { FilterTabButton(text = tabMedium, isSelected = selectedFilter == "MEDIUM", onClick = { selectedFilter = "MEDIUM" }) }
                    item { FilterTabButton(text = tabLow, isSelected = selectedFilter == "LOW", onClick = { selectedFilter = "LOW" }) }
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (filteredTasks.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize().weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = txtNoTask, color = Color.Gray)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = onAddTaskClick,
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text(text = txtCreateTask)
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 24.dp)
                    ) {
                        items(filteredTasks, key = { it.id }) { task ->
                            val dynamicPriorityColor = when (task.priority.uppercase()) {
                                "HIGH", "TINGGI" -> Color(0xFFEF4444)
                                "MEDIUM", "SEDANG" -> Color(0xFFF59E0B)
                                else -> Color(0xFF22C55E)
                            }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onTaskClick(task.id) },
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                border = BorderStroke(1.dp, Color(0x1A888888))
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Box(modifier = Modifier.width(5.dp).fillMaxHeight().background(dynamicPriorityColor))

                                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Card(
                                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                                                shape = RoundedCornerShape(6.dp)
                                            ) {
                                                Text(
                                                    text = task.category,
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = DynamicBrandBlue
                                                )
                                            }
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Box(modifier = Modifier.size(6.dp).background(dynamicPriorityColor, RoundedCornerShape(50.dp)))
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(text = task.priority.uppercase(), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(12.dp))

                                        Text(
                                            text = task.title,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontSize = 17.sp
                                        )

                                        if (task.description.isNotBlank()) {
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(text = task.description, fontSize = 13.sp, color = Color.Gray, lineHeight = 18.sp)
                                        }

                                        Spacer(modifier = Modifier.height(16.dp))

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(text = "📅", fontSize = 13.sp)
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(
                                                    text = task.deadline,
                                                    fontSize = 13.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }

                                            Card(
                                                shape = RoundedCornerShape(50.dp),
                                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                                                modifier = Modifier.size(32.dp)
                                            ) {
                                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                                    Icon(
                                                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                                        contentDescription = "Detail",
                                                        modifier = Modifier.size(18.dp),
                                                        tint = DynamicBrandBlue
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun FilterTabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    if (isSelected) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            ),
            shape = RoundedCornerShape(50.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // 🟢 Karakter '延' misterius di sini sudah dibuang sepenuhnya!
            Text(text = text, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, Color(0x1A888888)),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = text, color = MaterialTheme.colorScheme.onSurface, fontSize = 13.sp)
        }
    }
}