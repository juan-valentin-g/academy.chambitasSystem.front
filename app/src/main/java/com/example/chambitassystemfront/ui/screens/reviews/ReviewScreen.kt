package com.example.chambitassystemfront.ui.screens.reviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chambitassystemfront.data.model.MatchResponseDto
import com.example.chambitassystemfront.data.session.SessionManager

@Composable
fun ReviewScreen(
    match: MatchResponseDto?,
    reviewViewModel: ReviewViewModel,
    onSubmitReview: () -> Unit,
    onBackClick: () -> Unit
) {
    var rating by remember { mutableIntStateOf(5) }
    var comment by remember { mutableStateOf("") }
    var localError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(match?.id) {
        match?.let { reviewViewModel.fetchMatchReviews(it.id) }
    }

    val ownReview = match?.let { currentMatch ->
        reviewViewModel.matchReviews.firstOrNull {
            it.matchId == currentMatch.id && it.reviewerId == SessionManager.userId
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F7FF))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(Color.White).padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Regresar"
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text("Reseña y calificación", fontSize = 21.sp, fontWeight = FontWeight.Bold)
                Text("Comparte tu experiencia", fontSize = 12.sp, color = Color.Gray)
            }
            Icon(
                Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFFFB300),
                modifier = Modifier.size(30.dp)
            )
        }

        Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        match?.job?.titulo ?: "Trabajo #${match?.jobId ?: 0}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF2E9B57)
                        )
                        Text(
                            " Match finalizado",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (ownReview != null) {
                Text(
                    text = "Ya publicaste una reseña para este match.",
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text("Calificación: ${ownReview.calificacion}/5")
                Text(ownReview.comentario)
                return@Column
            }

            Text("¿Cómo fue tu experiencia?", fontSize = 21.sp, fontWeight = FontWeight.Bold)
            Text("Selecciona una calificación de 1 a 5 estrellas.", color = Color.Gray)

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                for (value in 1..5) {
                    IconButton(onClick = { rating = value }) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "Estrella $value",
                            tint = if (value <= rating) {
                                Color(0xFFFFB300)
                            } else {
                                Color(0xFFD5D5D5)
                            },
                            modifier = Modifier.size(38.dp)
                        )
                    }
                }
            }

            Text(
                text = "$rating de 5 estrellas",
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF4B20C9),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(18.dp))

            OutlinedTextField(
                value = comment,
                onValueChange = {
                    if (it.length <= 2000) {
                        comment = it
                        localError = null
                    }
                },
                label = { Text("Escribe una reseña") },
                modifier = Modifier.fillMaxWidth().height(140.dp),
                shape = RoundedCornerShape(16.dp),
                maxLines = 5
            )
            Text(
                text = "${comment.length}/2000",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                color = Color.Gray,
                fontSize = 12.sp
            )

            val visibleError = localError ?: reviewViewModel.errorMessage
            visibleError?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(it, color = Color(0xFFD32F2F))
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                enabled = !reviewViewModel.isLoading && match != null,
                onClick = {
                    when {
                        match == null -> localError = "No se encontró el match"
                        match.estado != "FINALIZADO" ->
                            localError = "El trabajo todavía no está finalizado"
                        comment.isBlank() ->
                            localError = "Escribe un comentario antes de enviar"
                        else -> reviewViewModel.createReview(
                            matchId = match.id,
                            rating = rating,
                            comment = comment,
                            onSuccess = { onSubmitReview() },
                            onError = { localError = it }
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth().height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4B20C9))
            ) {
                if (reviewViewModel.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Icon(Icons.Default.Star, contentDescription = null)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text("Enviar reseña")
                }
            }
        }
    }
}
