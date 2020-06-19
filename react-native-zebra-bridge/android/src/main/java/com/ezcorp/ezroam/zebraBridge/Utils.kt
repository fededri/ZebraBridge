package com.ezcorp.ezroam.zebraBridge

import com.ezcorp.ezroam.zebraBridge.di.ApplicationSingleton
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.zebra.sdk.printer.PrinterStatus

fun getConnection() = ApplicationSingleton.printerConnection


//Here we map all the important printer status key-value information
fun PrinterStatus.asMap(): WritableMap {
    return Arguments.createMap().apply {
        putString("READY", isReadyToPrint.toString())
        putString("LABELS IN BATCH", labelsRemainingInBatch.toString())
        putString("NUMBER OF FORMATS IN BUFFER", numberOfFormatsInReceiveBuffer.toString())
        putString("IS PAPER OUT", isPaperOut.toString())
        putString("IS HEAD COLD", isHeadCold.toString())
    }
}