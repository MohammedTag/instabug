package com.task.instabug.domain.usecases

import com.task.instabug.data.models.WordPresentationModel
import com.task.instabug.domain.repositories.WordsRepository

class GetWordsUseCase(private val WordsRepository: WordsRepository) {

    fun perform(): WordPresentationModel {
        return WordsRepository.fetchData()
    }
}