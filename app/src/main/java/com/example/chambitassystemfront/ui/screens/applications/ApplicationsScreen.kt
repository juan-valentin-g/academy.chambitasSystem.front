package com.example.chambitassystemfront.ui.screens.applications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chambitassystemfront.data.model.ApplicationResponseDto
import com.example.chambitassystemfront.data.session.SessionManager
import com.example.chambitassystemfront.ui.screens.jobs.JobViewModel

@Composable
fun ApplicationsScreen(
    applicationsViewModel: ApplicationsViewModel,
    jobViewModel: JobViewModel,
    onMatchClick: (Int) -> Unit,
    onOpenMatchesClick: () -> Unit,
    onBackClick: () -> Unit,
    onJobClick: (Int) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var operationError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        jobViewModel.setCurrentUserId(SessionManager.userId)
        jobViewModel.fetchMyJobs()
        applicationsViewModel.fetchSentApplications { applications ->
            jobViewModel.syncAppliedJobs(applications.map { it.jobId })
        }
    }

    val myJobIds = jobViewModel.myPublications.map { it.id }.sorted()
    LaunchedEffect(myJobIds) {
        applicationsViewModel.fetchApplicationsForMyJobs(myJobIds)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F7FF))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 20.dp, top = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Regresar"
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Postulaciones",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        PrimaryTabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.Transparent
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Enviadas") },
                icon = {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null)
                }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Recibidas") },
                icon = { Icon(Icons.Default.Inbox, contentDescription = null) }
            )
        }

        val visibleError = operationError ?: applicationsViewModel.errorMessage
        visibleError?.let {
            Text(
                text = it,
                color = Color(0xFFD32F2F),
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )
        }

        if (applicationsViewModel.isLoading &&
            applicationsViewModel.sentApplications.isEmpty() &&
            applicationsViewModel.receivedApplications.isEmpty()
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Column
        }

        if (selectedTab == 0) {
            ApplicationList(
                title = "Trabajos a los que me postulé",
                emptyMessage = "No has enviado postulaciones",
                applications = applicationsViewModel.sentApplications,
                jobViewModel = jobViewModel,
                onCardClick = { application ->
                    if (application.estado.equals("ACEPTADA", ignoreCase = true)) {
                        onOpenMatchesClick()
                    } else if (application.estado.equals("PENDIENTE", ignoreCase = true)) {
                        onJobClick(application.jobId)
                    }
                }
            )
        } else {
            ApplicationList(
                title = "Postulaciones recibidas",
                emptyMessage = "Tus publicaciones no tienen postulaciones",
                applications = applicationsViewModel.receivedApplications,
                jobViewModel = jobViewModel,
                onCardClick = { application ->
                    if (application.estado.equals("ACEPTADA", ignoreCase = true)) {
                        onOpenMatchesClick()
                    } else if (application.estado.equals("PENDIENTE", ignoreCase = true)) {
                        onJobClick(application.jobId)
                    }
                },
                onAccept = { application ->
                    operationError = null
                    applicationsViewModel.acceptApplication(
                        application = application,
                        onSuccess = { match -> onMatchClick(match.id) },
                        onError = { operationError = it }
                    )
                },
                onReject = { application ->
                    operationError = null
                    applicationsViewModel.rejectApplication(
                        application = application,
                        onSuccess = {},
                        onError = { operationError = it }
                    )
                }
            )
        }
    }
}
@Composable
private fun ApplicationList(
    title: String,
    emptyMessage: String,
    applications: List<ApplicationResponseDto>,
    jobViewModel: JobViewModel,
    onCardClick: (ApplicationResponseDto) -> Unit,
    onAccept: ((ApplicationResponseDto) -> Unit)? = null,
    onReject: ((ApplicationResponseDto) -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))

        if (applications.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(emptyMessage, color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(applications, key = { it.id }) { application ->
                    val cachedJob = jobViewModel.myPublications
                        .firstOrNull { it.id == application.jobId }
                    ApplicationCard(
                        title = application.job?.titulo
                            ?: cachedJob?.titulo
                            ?: "Trabajo #${application.jobId}",
                        subtitle = application.applicant?.nombre
                            ?.let { "Postulante: $it" }
                            ?: application.mensaje
                            ?: "Sin mensaje",
                        application = application,
                        onCardClick = { onCardClick(application) },
                        onAcceptClick = onAccept?.let { { it(application) } },
                        onRejectClick = onReject?.let { { it(application) } }
                    )
                }
            }
        }
    }
}

@Composable
private fun ApplicationCard(
    title: String,
    subtitle: String,
    application: ApplicationResponseDto,
    onCardClick: () -> Unit,
    onAcceptClick: (() -> Unit)?,
    onRejectClick: (() -> Unit)?
) {
    val normalizedStatus = application.estado.uppercase()
    val statusColor = when (normalizedStatus) {
        "ACEPTADA" -> Color(0xFF2E7D32)
        "RECHAZADA" -> Color(0xFFD32F2F)
        else -> Color(0xFFFFA000)
    }
    val canResolve = normalizedStatus == "PENDIENTE" &&
        onAcceptClick != null &&
        onRejectClick != null

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        onClick = onCardClick
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFEAE2FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Work,
                        contentDescription = null,
                        tint = Color(0xFF4B20C9),
                        modifier = Modifier.size(26.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(title, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(subtitle, fontSize = 13.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = statusColor,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Estado: ${application.estado}",
                    color = statusColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            if (canResolve) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onRejectClick,
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        Text("Rechazar", color = Color(0xFFD32F2F))
                    }
                    Button(
                        onClick = onAcceptClick,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4B20C9)
                        ),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        Text("Aceptar")
                    }
                }
            }
        }
    }
}
