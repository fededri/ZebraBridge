package com.ezcorp.ezroam.zebraBridge.implementations

import com.ezcorp.ezroam.zebraBridge.dispatchNewTask
import com.ezcorp.ezroam.zebraBridge.getConnection
import com.ezcorp.ezroam.zebraBridge.interfaces.FileSender
import com.ezcorp.ezroam.zebraBridge.models.ResolveTypes
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.zebra.sdk.printer.ZebraPrinterFactory

class FileSenderImplementation : FileSender {

    override fun sendFile(absolutePath: String, promise: Promise) {
        dispatchNewTask(Runnable {
            val printer = ZebraPrinterFactory.getInstance(getConnection())

            printer.sendFileContents(absolutePath)
            promise.resolve(ResolveTypes.PRINT_SENT.value)
        })
    }
}