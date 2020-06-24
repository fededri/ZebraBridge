package com.ezcorp.ezroam.zebraBridge.di

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

    fun initializeKoin(context: ReactApplicationContext) {
        koinApplication = koinApplication {
            androidContext(context)
            modules(appModule)
        }
    }
}


interface CustomKoinComponent : KoinComponent {
    override fun getKoin(): Koin {
        return ApplicationSingleton.koinApplication!!.koin
    }
}