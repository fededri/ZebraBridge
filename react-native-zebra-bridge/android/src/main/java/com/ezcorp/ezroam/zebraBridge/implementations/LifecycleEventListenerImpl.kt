package com.ezcorp.ezroam.zebraBridge.implementations

import android.util.Log
import com.ezcorp.ezroam.zebraBridge.di.ApplicationSingleton
import com.facebook.react.bridge.LifecycleEventListener

class LifecycleEventListenerImpl : LifecycleEventListener {

    //Clean up all resources
    override fun onHostDestroy() {
        try{
            ApplicationSingleton.printerConnection?.close()
        } finally {
            ApplicationSingleton.printerConnection = null
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