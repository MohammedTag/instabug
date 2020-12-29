package com.task.instabug.domain.repositories

import com.task.instabug.data.models.WordPresentationModel

interface WordsRepository {

    fun fetchData(): WordPresentationModel
}