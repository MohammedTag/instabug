package com.task.instabug.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.task.instabug.R
import com.task.instabug.app.InstaBugApp
import com.task.instabug.data.models.WordPresentationModel
import com.task.instabug.di.AppDi
import com.task.instabug.di.PresentationDI
import com.task.instabug.presentation.models.WordsListEventData
import com.task.instabug.presentation.viewmodels.WordsFragmentViewModel
import com.task.instabug.ui.adapter.WordsListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appDi: AppDi
    private lateinit var viewModel: WordsFragmentViewModel
    private val wordsAdapter = WordsListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appDi = (application as InstaBugApp).appDi
        appDi.presentationDi = PresentationDI(appDi.dataDi)
        appDi.presentationDi?.viewModelFactory?.create()?.let {
            viewModel = it
        }

        viewModel.fetchMappedResponse()
        viewModel.receivedData.observe(this, Observer {
            when (it) {
                WordsListEventData.LoadingState -> loadingStatus(true)

                is WordsListEventData.SuccessState -> handleSuccessState(it.data)

                is WordsListEventData.ErrorState -> handleErrorState()
            }
        })
    }

    private fun loadingStatus(status: Boolean) {
        when (status) {
            true -> progressBar.visibility = View.VISIBLE

            false -> progressBar.visibility = View.GONE
        }
    }

    private fun handleSuccessState(data: WordPresentationModel) {
        loadingStatus(false)
       wordsRV.adapter = wordsAdapter
        wordsAdapter.updateHashMap(data.data)
    }

    private fun handleErrorState() {
        loadingStatus(false)
        val errorHandler = com.task.instabug.ui.errors.ErrorHandler(this)
        errorHandler.handleError()
    }
}