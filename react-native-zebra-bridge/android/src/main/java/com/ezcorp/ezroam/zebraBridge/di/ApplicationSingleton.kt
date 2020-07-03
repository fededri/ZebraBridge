package com.ezcorp.ezroam.zebraBridge.di

import android.content.ContentResolver
import com.facebook.react.bridge.ReactApplicationContext
import com.zebra.sdk.comm.Connection
import kotlinx.coroutines.Job
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.KoinComponent
import org.koin.dsl.koinApplication

object ApplicationSingleton {

    var printerConnection: Connection? = null
    var jobs = mutableListOf<Job>()
    var koinApplication: KoinApplication? = null
    var contentResolver : ContentResolver ? = null

    fun initializeKoin(context: ReactApplicationContext) {
        contentResolver = context.contentResolver
        koinApplication = koinApplication {
            androidContext(context)
            modules(appModule)
        }
    }
}