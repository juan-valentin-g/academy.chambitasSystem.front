package com.example.chambitassystemfront.ui.screens.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.chambitassystemfront.data.repository.JobsRepository

class JobViewModelFactory(private val jobsRepository: JobsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(JobViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JobViewModel(jobsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}