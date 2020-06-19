package com.ezcorp.ezroam.zebraBridge.di

import com.ezcorp.ezroam.zebraBridge.ZebraBridgeModule
import com.ezcorp.ezroam.zebraBridge.implementations.ConnectorImplementation
import com.ezcorp.ezroam.zebraBridge.implementations.FileSenderImplementation
import com.ezcorp.ezroam.zebraBridge.implementations.LifecycleEventListenerImpl
import com.ezcorp.ezroam.zebraBridge.interfaces.FileSender
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.zebra.sdk.comm.Connection
import kotlinx.coroutines.Job

object ApplicationSingleton {

    var printerConnection: Connection? = null
    var jobs = mutableListOf<Job>()

    fun getZebraModule(context: ReactApplicationContext): ZebraBridgeModule {
        return ZebraBridgeModule(context,
                ConnectorImplementation(),
                FileSenderImplementation(context),
                LifecycleEventListenerImpl()
        )
    }
}