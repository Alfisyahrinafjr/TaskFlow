package com.example.taskflow.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskflow.domain.model.Task
import com.example.taskflow.ui.viewmodel.TaskViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    viewModel: TaskViewModel,
    onSaveSuccess: () -> Unit,
    taskId: String? = null
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val locale = context.resources.configuration.locales[0]
    val isIndonesian = locale.language == "in" || locale.language == "id"

    val defaultCategory = if (isIndonesian) "Kuliah" else "Engineering"

    // State form utama
    var taskTitle by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(defaultCategory) }
    var deadline by remember { mutableStateOf("mm/dd/yyyy") }
    var selectedPriority by remember { mutableStateOf("Medium") }
    var isCompletedState by remember { mutableStateOf(false) }

    // LOAD DATA LAMA JIKA MODE EDIT
    LaunchedEffect(taskId) {
        if (taskId != null) {
            viewModel.getTaskById(taskId) { existingTask ->
                existingTask?.let {
                    taskTitle = it.title
                    description = it.description
                    category = it.category
                    deadline = it.deadline
                    selectedPriority = it.priority
                    isCompletedState = it.isCompleted
                }
            }
        }
    }

    val scrollState = rememberScrollState()

    val txtHeadline = if (taskId == null) {
        if (isIndonesian) "Rancang\nHari Anda" else "Architect\nYour Day"
    } else {
        if (isIndonesian) "Perbarui\nTugas Anda" else "Modify\nYour Task"
    }

    val txtDesc = if (taskId == null) {
        if (isIndonesian) {
            "Presisi adalah fondasi produktivitas. Tetapkan tujuan Anda, atur batasan Anda, dan bangun alur kerja yang berkembang seiring dengan ambisi Anda."
        } else {
            "Precision is the foundation of productivity. Define your objectives, set your constraints, and build a workflow that scales with your ambition."
        }
    } else {
        if (isIndonesian) {
            "Perubahan rencana adalah hal yang wajar. Sesuaikan parameter tugas Anda di bawah ini untuk menjaga akurasi manajemen pengerjaan."
        } else {
            "Course corrections are natural. Adjust your operational parameters below to keep your execution metrics razor-sharp."
        }
    }

    val txtPlaceTitle = if (isIndonesian) "Tentukan tujuan utama" else "Specify the core objective"
    val txtPlaceDesc = if (isIndonesian) "Detail kebutuhan teknis dan konteks..." else "Detail the technical requirements and context..."

    val txtBtnSave = if (taskId == null) {
        if (isIndonesian) "Simpan Tugas" else "Save Task"
    } else {
        if (isIndonesian) "Simpan Perubahan" else "Save Changes"
    }

    val labelLow = if (isIndonesian) "Rendah" else "Low"
    val labelMedium = if (isIndonesian) "Sedang" else "Medium"
    val labelHigh = if (isIndonesian) "Tinggi" else "High"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Badge Atas
        Surface(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
            shape = RoundedCornerShape(50.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (taskId == null) "EFFICIENCY SUITE" else "REVISION MODE",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = txtHeadline,
            fontSize = 42.sp,
            lineHeight = 48.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = txtDesc,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        CustomLabel(if (isIndonesian) "JUDUL TUGAS" else "TASK TITLE")
        CustomTextField(
            value = taskTitle,
            onValueChange = { taskTitle = it },
            placeholder = txtPlaceTitle
        )

        CustomLabel(if (isIndonesian) "DESKRIPSI" else "DESCRIPTION")
        CustomTextField(
            value = description,
            onValueChange = { description = it },
            placeholder = txtPlaceDesc,
            isMultiline = true
        )

        CustomLabel(if (isIndonesian) "KATEGORI" else "CATEGORY")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable { /* Handler Spinner */ }
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = category, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        CustomLabel("DEADLINE")
        CustomTextField(
            value = deadline,
            onValueChange = { deadline = it },
            placeholder = "mm/dd/yyyy"
        )

        CustomLabel(if (isIndonesian) "TINGKAT PRIORITAS" else "PRIORITY LEVEL")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PriorityButton(labelLow, selectedPriority == "Low" || selectedPriority == "Rendah", Modifier.weight(1f)) { selectedPriority = "Low" }
            PriorityButton(labelMedium, selectedPriority == "Medium" || selectedPriority == "Sedang", Modifier.weight(1f)) { selectedPriority = "Medium" }
        }
        Spacer(modifier = Modifier.height(12.dp))
        PriorityButton(labelHigh, selectedPriority == "High" || selectedPriority == "Tinggi", Modifier.fillMaxWidth()) { selectedPriority = "High" }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                if (taskTitle.isNotBlank()) {
                    val finalTask = Task(
                        id = taskId ?: UUID.randomUUID().toString(),
                        title = taskTitle,
                        description = description,
                        category = category,
                        deadline = deadline,
                        priority = selectedPriority,
                        isCompleted = isCompletedState
                    )

                    if (taskId == null) {
                        viewModel.insertTask(finalTask)
                    } else {
                        viewModel.updateTask(finalTask)
                    }
                    onSaveSuccess()
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = txtBtnSave, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun CustomLabel(text: String) {
    Text(
        text = text,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
        modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isMultiline: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f), fontSize = 14.sp) },
        modifier = Modifier
            .fillMaxWidth()
            .then(if (isMultiline) Modifier.height(120.dp) else Modifier),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = !isMultiline
    )
}

@Composable
fun PriorityButton(
    label: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant,
        border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isSelected) {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = label,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}