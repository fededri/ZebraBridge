package com.ezcorp.ezroam.zebraBridge.interfaces

import com.facebook.react.bridge.Promise
import com.facebook.react.modules.core.DeviceEventManagerModule

interface DiscoveryProtocol {
    fun discoverPrintersOnNetwork(eventEmitter: DeviceEventManagerModule.RCTDeviceEventEmitter, promise: Promise)
}