package com.task.instabug.di

import com.task.instabug.domain.usecases.GetWordsUseCase
import com.task.instabug.presentation.viewmodels.WordsFragmentViewModelFactory

class PresentationDI(dataContainer: DataDi) {

    private val useCase = GetWordsUseCase(dataContainer.repository)

    val viewModelFactory = WordsFragmentViewModelFactory(useCase)
}