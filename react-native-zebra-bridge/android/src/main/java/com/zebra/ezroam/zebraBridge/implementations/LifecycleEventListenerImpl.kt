package com.zebra.ezroam.zebraBridge.implementations

import com.zebra.ezroam.zebraBridge.di.ApplicationSingleton
import com.facebook.react.bridge.LifecycleEventListener

class LifecycleEventListenerImpl : LifecycleEventListener {

    //Clean up all resources
    override fun onHostDestroy() {
        try {
            ApplicationSingleton.apply {
                koinApplication?.close()
                printerConnection?.close()
            }
        } finally {
            ApplicationSingleton.printerConnection = null
            ApplicationSingleton.contentResolver = null
            ApplicationSingleton.jobs.forEach {
                it.cancel()
            }
        }
    }

    override fun onHostPause() {
    }

    override fun onHostResume() {
    }
}