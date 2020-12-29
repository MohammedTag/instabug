package com.task.instabug.data.repositories

import com.task.instabug.data.mappers.toPresentationWordModel
import com.task.instabug.data.models.WordPresentationModel
import com.task.instabug.domain.repositories.WordsRepository
import com.task.instabug.local.LocalDs
import com.task.instabug.network.models.WordsResponse
import com.task.instabug.network.request.RemoteDs
import com.task.instabug.network.state_provider.NetworkProvider

class WordsRepository(
    private val remoteDataSource: RemoteDs,
    private val localDataSource: LocalDs,
    private val networkStateProvider: NetworkProvider
) : WordsRepository {

    override fun fetchData(): WordPresentationModel {
        val rawData: WordsResponse
        var data: WordPresentationModel? = null
        when (networkStateProvider.isConnected()) {
            true -> {
                rawData = remoteDataSource.fetchHtmlResponse()
                data = rawData.toPresentationWordModel()
                localDataSource.saveWords(rawData)
            }
            false -> {
                data = localDataSource.getCachedResult().toPresentationWordModel()
            }
        }
        return data
    }
}