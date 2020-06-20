package com.ezcorp.ezroam.zebraBridge.implementations

import com.ezcorp.ezroam.zebraBridge.di.ApplicationSingleton
import com.ezcorp.ezroam.zebraBridge.dispatchNewTask
import com.ezcorp.ezroam.zebraBridge.getConnection
import com.ezcorp.ezroam.zebraBridge.interfaces.ConnectionProtocol
import com.ezcorp.ezroam.zebraBridge.models.ErrorTypes
import com.ezcorp.ezroam.zebraBridge.models.ResolveTypes
import com.facebook.react.bridge.Promise
import com.zebra.sdk.comm.TcpConnection

class ConnectorImplementation : ConnectionProtocol {

    override fun connect(ip: String, port: Int, promise: Promise) {
        dispatchNewTask(Runnable {
            try {
                val printerConnection = TcpConnection(ip, port)
                printerConnection.open()
                ApplicationSingleton.printerConnection = printerConnection
                promise.resolve(ResolveTypes.SUCCESS.value)
            } catch (e: Exception) {
                promise.reject(e)
            }
        })
    }


    override fun disconnect(promise: Promise) {
        dispatchNewTask(Runnable {
            getConnection()?.close()?.let {
                promise.resolve(ResolveTypes.SUCCESS.value)
            } ?: promise.reject(ErrorTypes.NO_CONNECTION_FOUND.value, "First you must be connected")
        })
    }

    override fun isConnectedToPrinter(promise: Promise) {
        val isConnected = getConnection()?.isConnected ?: false
        promise.resolve(isConnected)
    }

    override fun isConnected(): Boolean {
        return getConnection()?.isConnected ?: false
    }
}



