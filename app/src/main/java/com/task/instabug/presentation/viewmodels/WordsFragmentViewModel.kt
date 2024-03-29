package com.task.instabug.presentation.viewmodels

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.instabug.di.Factory
import com.task.instabug.domain.usecases.GetWordsUseCase
import com.task.instabug.presentation.models.WordsListEventData
import java.io.IOException
import java.util.concurrent.Executors

class WordsFragmentViewModel(private val getWordsUseCase: GetWordsUseCase) : ViewModel() {
    val receivedData = MutableLiveData<WordsListEventData>()

    fun fetchMappedResponse() {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        receivedData.value = WordsListEventData.LoadingState

        executor.execute {
            try {
                val response = getWordsUseCase.perform()

                handler.post {
                    receivedData.value = WordsListEventData.SuccessState(response)
                }
            } catch (e: IOException) {
                handler.post {
                    receivedData.value = WordsListEventData.ErrorState(e)
                }
            }
        }
    }
}

class WordsFragmentViewModelFactory(private val getWordsUseCase: GetWordsUseCase):
    Factory<WordsFragmentViewModel> {

    override fun create(): WordsFragmentViewModel {
        return WordsFragmentViewModel(getWordsUseCase)
    }
}