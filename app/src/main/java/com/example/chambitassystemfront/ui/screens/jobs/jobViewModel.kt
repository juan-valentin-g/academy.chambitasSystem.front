package com.example.chambitassystemfront.ui.screens.jobs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chambitassystemfront.data.model.CreateApplicationDto
import com.example.chambitassystemfront.data.model.CreateJobDto
import com.example.chambitassystemfront.data.model.JobResponseDto
import com.example.chambitassystemfront.data.model.UpdateJobDto
import com.example.chambitassystemfront.data.repository.JobsRepository
import kotlinx.coroutines.launch

class JobViewModel(private val jobsRepository: JobsRepository) : ViewModel() {

    private var allJobs: List<JobResponseDto> = emptyList()
    private var ownedJobs: List<JobResponseDto> = emptyList()
    private var allSearchResults: List<JobResponseDto> = emptyList()

    var jobs by mutableStateOf<List<JobResponseDto>>(emptyList())
        private set

    var myPublications by mutableStateOf<List<JobResponseDto>>(emptyList())
        private set

    var myApplications by mutableStateOf<List<JobResponseDto>>(emptyList())
        private set

    var searchResults by mutableStateOf<List<JobResponseDto>>(emptyList())
        private set

    var selectedJob by mutableStateOf<JobResponseDto?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private val appliedJobIds = mutableSetOf<Int>()
    private var currentUserId: Int = 0

    fun setCurrentUserId(userId: Int) {
        currentUserId = userId
        applyFilters()
    }

    fun markAsApplied(jobId: Int) {
        appliedJobIds.add(jobId)
        applyFilters()
    }

    fun syncAppliedJobs(jobIds: Collection<Int>) {
        appliedJobIds.clear()
        appliedJobIds.addAll(jobIds)
        applyFilters()
    }

    private fun applyFilters() {
        myPublications = ownedJobs
        myApplications = (allJobs + ownedJobs)
            .distinctBy { it.id }
            .filter { appliedJobIds.contains(it.id) }
        jobs = filterAvailableJobs(allJobs)
        searchResults = filterAvailableJobs(allSearchResults)
    }

    private fun filterAvailableJobs(source: List<JobResponseDto>) = source.filter { job ->
        (currentUserId <= 0 || job.ownerId != currentUserId) &&
            !appliedJobIds.contains(job.id) &&
            job.estado.equals("PUBLICADO", ignoreCase = true)
    }

    fun fetchJobs() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            jobsRepository.getJobs().fold(
                onSuccess = { list ->
                    allJobs = list
                    applyFilters()
                    if (currentUserId > 0) {
                        jobsRepository.getMyApplications().onSuccess { applications ->
                            syncAppliedJobs(applications.map { it.jobId })
                        }
                    }
                },
                onFailure = { errorMessage = it.message }
            )
            isLoading = false
        }
    }

    fun fetchMyJobs() {
        viewModelScope.launch {
            isLoading = true
            jobsRepository.getMyJobs().fold(
                onSuccess = { list ->
                    ownedJobs = list
                    applyFilters()
                },
                onFailure = { errorMessage = it.message }
            )
            isLoading = false
        }
    }

    fun searchJobs(
        titulo: String? = null,
        categoryId: Int? = null,
        ubicacion: String? = null
    ) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            jobsRepository.getJobs(titulo, categoryId, ubicacion).fold(
                onSuccess = { list ->
                    allSearchResults = list
                    applyFilters()
                },
                onFailure = { errorMessage = it.message }
            )
            isLoading = false
        }
    }

    fun fetchJobById(jobId: Int) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            jobsRepository.getJobById(jobId).fold(
                onSuccess = { selectedJob = it },
                onFailure = { errorMessage = it.message }
            )
            isLoading = false
        }
    }

    fun createJob(
        categoryId: Int,
        titulo: String,
        descripcion: String,
        presupuesto: Double?,
        ubicacion: String?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            val request = CreateJobDto(
                categoryId = categoryId,
                titulo = titulo.trim(),
                descripcion = descripcion.trim(),
                presupuesto = presupuesto,
                ubicacion = ubicacion?.trim()
            )

            jobsRepository.createJob(request).fold(
                onSuccess = { createdJob ->
                    allJobs = listOf(createdJob) + allJobs
                    ownedJobs = listOf(createdJob) + ownedJobs
                    applyFilters()
                    onSuccess()
                },
                onFailure = { onError(it.message ?: "No se pudo crear el trabajo") }
            )
            isLoading = false
        }
    }

    fun updateJob(
        jobId: Int,
        categoryId: Int,
        titulo: String,
        descripcion: String,
        presupuesto: Double?,
        ubicacion: String?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            val request = UpdateJobDto(
                categoryId = categoryId,
                titulo = titulo.trim(),
                descripcion = descripcion.trim(),
                presupuesto = presupuesto,
                ubicacion = ubicacion?.trim()
            )

            jobsRepository.updateJob(jobId, request).fold(
                onSuccess = { updated ->
                    selectedJob = updated
                    allJobs = allJobs.map { if (it.id == updated.id) updated else it }
                    ownedJobs = ownedJobs.map { if (it.id == updated.id) updated else it }
                    allSearchResults = allSearchResults.map { if (it.id == updated.id) updated else it }
                    applyFilters()
                    onSuccess()
                },
                onFailure = { onError(it.message ?: "No se pudo actualizar el trabajo") }
            )
            isLoading = false
        }
    }

    fun deleteJob(
        jobId: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            jobsRepository.deleteJob(jobId).fold(
                onSuccess = {
                    allJobs = allJobs.filterNot { it.id == jobId }
                    ownedJobs = ownedJobs.filterNot { it.id == jobId }
                    allSearchResults = allSearchResults.filterNot { it.id == jobId }
                    if (selectedJob?.id == jobId) selectedJob = null
                    applyFilters()
                    onSuccess()
                },
                onFailure = { onError(it.message ?: "No se pudo eliminar el trabajo") }
            )
            isLoading = false
        }
    }

    fun applyToJob(
        jobId: Int,
        mensaje: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            val request = CreateApplicationDto(mensaje = mensaje)
            jobsRepository.applyToJob(jobId, request).fold(
                onSuccess = {
                    markAsApplied(jobId)
                    onSuccess()
                },
                onFailure = { onError(it.message ?: "No se pudo enviar la postulacion") }
            )
            isLoading = false
        }
    }
}
