package com.example.taskflow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskflow.domain.model.Task
import com.example.taskflow.ui.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: String?,
    viewModel: TaskViewModel,
    onBackClick: () -> Unit,
    onEditClick: (String) -> Unit
) {
    val context = LocalContext.current
    val locale = context.resources.configuration.locales[0]
    val isIndonesian = locale.language == "in" || locale.language == "id"

    var task by remember { mutableStateOf<Task?>(null) }

    LaunchedEffect(taskId) {
        taskId?.let { id ->
            viewModel.getTaskById(id) { result ->
                task = result
            }
        }
    }

    val txtScreenTitle = if (isIndonesian) "Detail Tugas" else "Mission Details"
    val txtLabelRequirements = if (isIndonesian) "PERSYARATAN OPERASIONAL" else "OPERATIONAL REQUIREMENTS"
    val txtNoDescription = if (isIndonesian) "Tidak ada persyaratan tambahan." else "No additional requirements specified."
    val txtPriority = if (isIndonesian) "PRIORITAS" else "PRIORITY"

    val txtBtnComplete = if (isIndonesian) "Selesaikan Tugas" else "Complete Task"
    val txtBtnAlreadyComplete = if (isIndonesian) "Tugas Sudah Selesai" else "Task Already Completed"
    val txtBtnEdit = if (isIndonesian) "Edit" else "Edit"
    val txtBtnDelete = if (isIndonesian) "Hapus" else "Delete"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = txtScreenTitle, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.onBackground) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onBackground)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            if (task == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else {
                val currentTask = task!!

                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = currentTask.category.uppercase(),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = currentTask.title,
                    fontSize = 28.sp,
                    lineHeight = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val priorityValue = currentTask.priority.uppercase()
                    val translatedPriority = when(priorityValue) {
                        "HIGH" -> if (isIndonesian) "TINGGI" else "HIGH"
                        "MEDIUM" -> if (isIndonesian) "SEDANG" else "MEDIUM"
                        else -> if (isIndonesian) "RENDAH" else "LOW"
                    }

                    InfoBadge(label = "DEADLINE", value = currentTask.deadline, Modifier.weight(1f))
                    InfoBadge(
                        label = txtPriority,
                        value = translatedPriority,
                        Modifier.weight(1f),
                        valueColor = when(priorityValue) {
                            "HIGH", "TINGGI" -> Color(0xFFEF4444)
                            "MEDIUM", "SEDANG" -> Color(0xFFF59E0B)
                            else -> Color(0xFF22C55E)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(text = txtLabelRequirements, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = currentTask.description.ifBlank { txtNoDescription },
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    lineHeight = 24.sp,
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = {

                        val updatedTask = currentTask.copy(isCompleted = true)
                        viewModel.updateTask(updatedTask)
                        onBackClick()
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    enabled = !currentTask.isCompleted,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF22C55E),
                        disabledContainerColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (currentTask.isCompleted) txtBtnAlreadyComplete else txtBtnComplete,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // 2. TOMBOL EDIT
                    Button(
                        onClick = { onEditClick(currentTask.id) },
                        modifier = Modifier.weight(1f).height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = txtBtnEdit, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }

                    Button(
                        onClick = {
                            viewModel.deleteTask(currentTask)
                            onBackClick()
                        },
                        modifier = Modifier.weight(1f).height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = txtBtnDelete, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoBadge(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    valueColor: Color = Color.Unspecified
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (valueColor == Color.Unspecified) MaterialTheme.colorScheme.onSurfaceVariant else valueColor
            )
        }
    }
}