package com.ezcorp.ezroam.zebraBridge

import com.ezcorp.ezroam.zebraBridge.interfaces.ConnectionProtocol
import com.ezcorp.ezroam.zebraBridge.interfaces.ZebraProtocol
import com.facebook.react.bridge.*

class ZebraBridgeModule(private val reactContext: ReactApplicationContext,
                        private val connectionProtocol: ConnectionProtocol
) : ReactContextBaseJavaModule(reactContext), ZebraProtocol, LifecycleEventListener {

    override fun getName(): String {
        return "ZebraBridge"
    }


    @ReactMethod
    override fun connect(ip: String, port: Int, promise: Promise) {
        connectionProtocol.connect(ip, port, promise)
    }

    @ReactMethod
    override fun disconnect(promise: Promise) {
        connectionProtocol.disconnect(promise)
    }

    @ReactMethod
    override fun isConnectedToPrinter(promise: Promise) {
        connectionProtocol.isConnectedToPrinter(promise)
    }

    override fun onHostDestroy() {

    }

    override fun onHostPause() {

    }

    override fun onHostResume() {

    }

}