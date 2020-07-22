package com.zebra.ezroam.zebraBridge.implementations

import android.content.Context
import android.net.wifi.WifiManager
import com.zebra.ezroam.zebraBridge.asMap
import com.zebra.ezroam.zebraBridge.dispatchNewTask
import com.zebra.ezroam.zebraBridge.interfaces.DiscoveryProtocol
import com.zebra.ezroam.zebraBridge.models.ErrorTypes
import com.zebra.ezroam.zebraBridge.models.ObservableTopics
import com.zebra.ezroam.zebraBridge.models.ResolveTypes
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.zebra.sdk.printer.discovery.DiscoveredPrinter
import com.zebra.sdk.printer.discovery.DiscoveryHandler
import com.zebra.sdk.printer.discovery.NetworkDiscoverer
import java.lang.Exception

class DiscoveryImplementation(private val reactContext: Context
) : DiscoveryProtocol, DiscoveryHandler {

    private var eventEmitter: DeviceEventManagerModule.RCTDeviceEventEmitter? = null

    override fun discoverPrintersOnNetwork(eventEmitter: DeviceEventManagerModule.RCTDeviceEventEmitter, promise: Promise) {
        this.eventEmitter = eventEmitter
        dispatchNewTask(Runnable {
            try {
                //the lock is made with the purpose of obtaining packets of multicast wifi communication
                //Without this, the app could not receive this packets because by default they are filtered
                val wifiManager = reactContext.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val lock = wifiManager.createMulticastLock("wifi_multicast_lock")
                lock.setReferenceCounted(true)
                lock.acquire()
                NetworkDiscoverer.findPrinters(this)
                lock.release()
                promise.resolve(ResolveTypes.SEARCHING_PRINTERS.value)
            } catch (e: Exception) {
                promise.reject(e)
            }
        })
    }

    override fun foundPrinter(printer: DiscoveredPrinter) {
        eventEmitter?.emit(ObservableTopics.PRINTERS_SEARCH.value, printer.asMap())
    }

    override fun discoveryError(error: String?) {
        val map = Arguments.createMap().apply {
            putString(ErrorTypes.SEARCH_PRINTER_ERROR.value, error.orEmpty())
        }
        eventEmitter?.emit(ObservableTopics.PRINTERS_SEARCH.value, map)
    }

    override fun discoveryFinished() {
        eventEmitter?.emit(ObservableTopics.PRINTERS_SEARCH.value, ResolveTypes.SEARCHING_PRINTERS_FINISHED.value)
    }
}