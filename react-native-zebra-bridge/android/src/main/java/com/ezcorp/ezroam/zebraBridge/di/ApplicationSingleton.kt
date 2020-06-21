package com.ezcorp.ezroam.zebraBridge.di

import com.ezcorp.ezroam.zebraBridge.ZebraBridgeModule
import com.ezcorp.ezroam.zebraBridge.implementations.ConnectorImplementation
import com.ezcorp.ezroam.zebraBridge.implementations.DiscoveryImplementation
import com.ezcorp.ezroam.zebraBridge.implementations.FileSenderImplementation
import com.ezcorp.ezroam.zebraBridge.implementations.LifecycleEventListenerImpl
import com.ezcorp.ezroam.zebraBridge.interfaces.ConnectionProtocol
import com.ezcorp.ezroam.zebraBridge.interfaces.DiscoveryProtocol
import com.ezcorp.ezroam.zebraBridge.interfaces.FileSender
import com.facebook.react.bridge.LifecycleEventListener
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.zebra.sdk.comm.Connection
import kotlinx.coroutines.Job
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject

object ApplicationSingleton {

    var printerConnection: Connection? = null
    var jobs = mutableListOf<Job>()


    fun getZebraModule(context: ReactApplicationContext): ZebraBridgeModule {
        return get(ZebraBridgeModule::class.java)
    }

    fun initializeKoin(context: ReactApplicationContext) {
        startKoin {
            androidContext(context)
            modules(appModule)
        }
    }
}