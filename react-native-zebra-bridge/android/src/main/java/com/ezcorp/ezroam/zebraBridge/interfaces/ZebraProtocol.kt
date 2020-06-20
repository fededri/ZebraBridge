package com.ezcorp.ezroam.zebraBridge.interfaces

import com.facebook.react.bridge.Promise

interface ZebraProtocol : ConnectionProtocol, FileSender, DiscoveryProtocol {

    fun startListeningPrinterStatus(promise: Promise)

    fun stopListeningPrinterStatus()
}