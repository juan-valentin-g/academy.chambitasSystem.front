package com.example.chambitassystemfront.ui.screens.jobs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chambitassystemfront.data.model.JobResponseDto
import com.example.chambitassystemfront.data.session.SessionManager

@Composable
fun JobDetailScreen(
    jobId: Int,
    jobViewModel: JobViewModel,
    onApplyClick: () -> Unit,
    onDeleteSuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var operationError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(jobId) {
        jobViewModel.fetchJobById(jobId)
    }

    val cachedJob = (
        jobViewModel.jobs +
            jobViewModel.myPublications +
            jobViewModel.myApplications
        ).firstOrNull { it.id == jobId }
    val job = jobViewModel.selectedJob?.takeIf { it.id == jobId } ?: cachedJob
    val isOwner = job?.ownerId == SessionManager.userId

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F7FF))
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Detalle del trabajo",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        when {
            jobViewModel.isLoading && job == null -> {
                Box(
                    modifier = Modifier.fillMaxWidth().height(240.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF4B20C9))
                }
            }

            job == null -> {
                Text(
                    text = jobViewModel.errorMessage
                        ?: "No se encontró la información del trabajo",
                    color = Color(0xFFD32F2F)
                )
            }

            else -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                        Box(
                            modifier = Modifier
                                .size(65.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFFEAE2FF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Work,
                                contentDescription = null,
                                tint = Color(0xFF4B20C9),
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(job.titulo, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))

                        JobInfoRow(
                            Icons.Default.AttachMoney,
                            "Presupuesto: $${job.presupuesto ?: 0.0}"
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        JobInfoRow(
                            Icons.Default.LocationOn,
                            job.ubicacion?.takeIf { it.isNotBlank() } ?: "Ubicación no especificada"
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        JobInfoRow(
                            Icons.Default.CalendarToday,
                            "Fecha: ${job.createdAt?.take(10) ?: "No disponible"}"
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        JobInfoRow(Icons.Default.Person, "Propietario: #${job.ownerId}")

                        Spacer(modifier = Modifier.height(24.dp))
                        Text("Descripción", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(job.descripcion, fontSize = 15.sp, color = Color.DarkGray)

                        operationError?.let {
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(it, color = Color(0xFFD32F2F))
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        if (isOwner) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Button(
                                    onClick = {
                                        operationError = null
                                        showEditDialog = true
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(Icons.Default.Edit, contentDescription = null)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Editar")
                                }
                                OutlinedButton(
                                    onClick = {
                                        operationError = null
                                        showDeleteDialog = true
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = Color(0xFFD32F2F)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Eliminar", color = Color(0xFFD32F2F))
                                }
                            }
                        } else {
                            Button(
                                onClick = onApplyClick,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF4B20C9)
                                )
                            ) {
                                Icon(Icons.Default.Work, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Postularme", fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showEditDialog && job != null) {
        EditJobDialog(
            job = job,
            isLoading = jobViewModel.isLoading,
            onDismiss = { showEditDialog = false },
            onSave = { title, description, budget, location ->
                jobViewModel.updateJob(
                    jobId = job.id,
                    categoryId = job.categoryId,
                    titulo = title,
                    descripcion = description,
                    presupuesto = budget,
                    ubicacion = location,
                    onSuccess = { showEditDialog = false },
                    onError = { operationError = it }
                )
            }
        )
    }

    if (showDeleteDialog && job != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar trabajo") },
            text = { Text("Esta acción eliminará la publicación. ¿Deseas continuar?") },
            confirmButton = {
                TextButton(
                    enabled = !jobViewModel.isLoading,
                    onClick = {
                        jobViewModel.deleteJob(
                            jobId = job.id,
                            onSuccess = {
                                showDeleteDialog = false
                                onDeleteSuccess()
                            },
                            onError = {
                                operationError = it
                                showDeleteDialog = false
                            }
                        )
                    }
                ) {
                    Text("Eliminar", color = Color(0xFFD32F2F))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
@Composable
private fun EditJobDialog(
    job: JobResponseDto,
    isLoading: Boolean,
    onDismiss: () -> Unit,
    onSave: (String, String, Double?, String?) -> Unit
) {
    var title by remember(job.id) { mutableStateOf(job.titulo) }
    var description by remember(job.id) { mutableStateOf(job.descripcion) }
    var budget by remember(job.id) { mutableStateOf(job.presupuesto?.toString().orEmpty()) }
    var location by remember(job.id) { mutableStateOf(job.ubicacion.orEmpty()) }
    var validationError by remember(job.id) { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar trabajo") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it; validationError = null },
                    label = { Text("Título") }
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it; validationError = null },
                    label = { Text("Descripción") }
                )
                OutlinedTextField(
                    value = budget,
                    onValueChange = { budget = it; validationError = null },
                    label = { Text("Presupuesto") }
                )
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Ubicación") }
                )
                validationError?.let { Text(it, color = Color(0xFFD32F2F)) }
            }
        },
        confirmButton = {
            TextButton(
                enabled = !isLoading,
                onClick = {
                    val parsedBudget = budget.toDoubleOrNull()
                    when {
                        title.isBlank() || description.isBlank() ->
                            validationError = "Título y descripción son obligatorios"
                        budget.isNotBlank() && (parsedBudget == null || parsedBudget <= 0) ->
                            validationError = "El presupuesto debe ser un número mayor a cero"
                        else -> onSave(
                            title,
                            description,
                            parsedBudget,
                            location.ifBlank { null }
                        )
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

@Composable
private fun JobInfoRow(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF4B20C9),
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = text, fontSize = 15.sp)
    }
}
