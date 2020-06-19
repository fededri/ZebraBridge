package com.ezcorp.ezroam.zebraBridge.implementations

import com.ezcorp.ezroam.zebraBridge.dispatchNewTask
import com.ezcorp.ezroam.zebraBridge.interfaces.ConnectionProtocol
import com.ezcorp.ezroam.zebraBridge.models.ErrorTypes
import com.ezcorp.ezroam.zebraBridge.models.ResolveTypes
import com.facebook.react.bridge.Promise
import com.zebra.sdk.comm.Connection
import com.zebra.sdk.comm.TcpConnection

class ConnectorImplementation : ConnectionProtocol {

    private var printerConnection: Connection? = null

    override fun connect(ip: String, port: Int, promise: Promise) {
        dispatchNewTask(Runnable {
            try {
                printerConnection = TcpConnection(ip, port)
                printerConnection?.open()
                promise.resolve(ResolveTypes.SUCCESS)
            } catch (e: Exception) {
                printerConnection = null
                promise.reject(e)
            }
        })
    }


    override fun disconnect(promise: Promise) {
        dispatchNewTask(Runnable {
            printerConnection?.close()?.let {
                promise.resolve(ResolveTypes.SUCCESS.value)
            } ?: promise.reject(ErrorTypes.NO_CONNECTION_FOUND.value, "First you must be connected")
        })
    }

    override fun isConnectedToPrinter(promise: Promise) {
        val isConnected = printerConnection?.isConnected ?: false
        promise.resolve(isConnected)
    }
}