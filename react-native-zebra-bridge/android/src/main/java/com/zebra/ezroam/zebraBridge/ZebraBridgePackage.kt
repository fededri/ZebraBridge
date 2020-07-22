package com.zebra.ezroam.zebraBridge

import com.zebra.ezroam.zebraBridge.di.ApplicationSingleton
import com.zebra.ezroam.zebraBridge.di.ApplicationSingleton.initializeKoin
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.JavaScriptModule
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager
import org.koin.core.Koin
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class ZebraBridgePackage : ReactPackage, KoinComponent {

    private val bridgeModule by inject<ZebraBridgeModule>()

    override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
        initializeKoin(reactContext)
        return Arrays.asList<NativeModule>(bridgeModule)
    }

    fun createJSModules(): List<Class<out JavaScriptModule?>> {
        return listOf()
    }

    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
        return emptyList()
    }


    override fun getKoin(): Koin {
        return ApplicationSingleton.koinApplication!!.koin
    }
}