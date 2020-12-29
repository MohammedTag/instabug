package com.task.instabug.di

import android.app.Application

class AppDi(context: Application) {

    val dataDi = DataDi(context)

    var presentationDi: PresentationDI? = null
}