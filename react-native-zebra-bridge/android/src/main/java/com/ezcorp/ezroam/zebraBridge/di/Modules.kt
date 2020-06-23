package com.ezcorp.ezroam.zebraBridge.di

import android.content.Context
import com.ezcorp.ezroam.zebraBridge.ZebraBridgeModule
import com.ezcorp.ezroam.zebraBridge.implementations.*
import com.ezcorp.ezroam.zebraBridge.interfaces.ConnectionProtocol
import com.ezcorp.ezroam.zebraBridge.interfaces.DiscoveryProtocol
import com.ezcorp.ezroam.zebraBridge.interfaces.FileSender
import com.ezcorp.ezroam.zebraBridge.interfaces.ImagePrinterProtocol
import com.facebook.react.bridge.LifecycleEventListener
import com.facebook.react.bridge.ReactApplicationContext
import org.koin.dsl.module

val appModule = module {
    factory<ConnectionProtocol> { ConnectorImplementation() }
    factory<FileSender> { FileSenderImplementation() }
    factory<LifecycleEventListener> { LifecycleEventListenerImpl() }
    factory<DiscoveryProtocol> { DiscoveryImplementation(get()) }
    factory<ReactApplicationContext> { get(Context::class.java) }
    factory<ImagePrinterProtocol> { ImagePrinterImplementation() }
    single { ZebraBridgeModule(get(), get(), get(), get(), get(), get()) }
}