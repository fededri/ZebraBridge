package com.ezcorp.ezroam.zebraBridge.interfaces

import com.facebook.react.bridge.Promise
import com.zebra.sdk.comm.ConnectionException


interface ConnectionProtocol {
    fun connect(ip: String, port: Int, promise: Promise)

    fun disconnect(promise: Promise)

    fun isConnectedToPrinter(promise: Promise)

    fun isConnected(): Boolean
}