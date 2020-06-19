package com.ezcorp.ezroam.zebraBridge.interfaces

import com.facebook.react.bridge.Promise

interface FileSender {
    fun sendFile(absolutePath: String, promise: Promise)
}